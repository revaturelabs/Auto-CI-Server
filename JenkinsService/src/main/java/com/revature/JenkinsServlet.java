package com.revature;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsServlet extends HttpServlet {
    String repoUrl;
    String projName;
    String slackChannel;
    String jenkinsUrl;
  
    final String jenkinsAuth = System.getenv("JENKINS_API_UTOKEN");
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Jenkins servlet started.");
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            makeContainerRepo(responseJson);
            makeJob(responseJson);
            log.info("makeJob method and job name added to JSON successfully.");
        } catch (Exception e) {
            responseJson.put("errorMsg", e.getMessage());
            log.error(e.getMessage());
        }
		
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toString(0));
        log.info("Jenkins servlet has written HTTP response and finished.");
    }

    private void parseParams(HttpServletRequest req) throws IOException {
        log.info("Reading JSON from HTTP request.");
        JSONObject json = parseRequestToJSON(req);
        parseJsonToVars(json);
    }

    private JSONObject parseRequestToJSON(HttpServletRequest req) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            log.error("Exception in parseRequestToJSON method. Issue reading the HTTP request.  " + e.getMessage());
            throw new IOException("Error reading input!");
        }

        JSONObject json = null;
        try {
            json = new JSONObject(jb.toString());
        } catch (JSONException e) {
            log.error("Request string is not JSON: " + jb.toString() + " " + e.getMessage());
            String err = "Request string is not JSON: " + jb.toString();
            throw new IOException(err);
        }
        return json;
    }

    private void parseJsonToVars(JSONObject json) throws IOException {
        try {
            repoUrl = json.getString("githubURL");
            projName = json.getString("projectName").toLowerCase();
            slackChannel = json.getString("slackChannel");
            jenkinsUrl = json.getString("jenkinsURL");
        } catch (JSONException e) {
            log.error("Exception when trying to parse JSON to Java.  " + e.getMessage());
            String err = "Error parsing JSON request string";
            if (json != null) {
                err += ": " + json.toString();
            }
            log.error("JSON exception occurring with this JSON: " + err);
            throw new IOException(err);
        }
    }
	
    private void makeJob(JSONObject response) throws IOException {
        log.info("Beginning makeJob method");
        CommandExecutor exec = new CommandExecutor();
        String cmd = "curl -X POST -u " + jenkinsAuth + " " + jenkinsUrl + "/job/seed/buildWithParameters --data githubURL=" + repoUrl + " --data projectName=" + projName + " --data slackChannel=" + slackChannel;
        String output = exec.execute(cmd);
        
        if (!exec.wasLastCmdSuccess()) {
            log.error("makeJob method failing due to cURL failure: " + output);
            throw new IOException(output);
        }
        response.put("devJob", projName + "/dev-pipe");
        response.put("prodJob", projName + "/prod-pipe");
    }
    
    private void makeContainerRepo(JSONObject response) throws IOException {
        log.info("Beginning createContainerRepo method");
        CommandExecutor exec = new CommandExecutor(getServletContext().getRealPath("/terra/"));

        // Copy template.txt to exec.tf, populate its fields
        Path templateFilePath = Path.of(getServletContext().getRealPath("/terra/template.txt"));
        Path execFilePath = Path.of(getServletContext().getRealPath("/terra/temp_exec.tf"));

        System.out.println("Writing temp file...");
        String templateText = Files.readString(templateFilePath);
        String execText = String.format(templateText, projName);
        Files.writeString(execFilePath, execText);

        String execOutput = "";

        System.out.println("terraform init...");
        execOutput = exec.execute("terraform init");
        log.info(execOutput);

        System.out.println("terraform apply...");
        execOutput = exec.execute("terraform apply -auto-approve");
        log.info(execOutput);
    }
}
