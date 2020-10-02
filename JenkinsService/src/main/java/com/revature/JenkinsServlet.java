package com.revature;

import java.io.BufferedReader;
import java.io.IOException;

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
  
    final String jenkinsAuth = "user:token";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Jenkins servlet started.");
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            makeJob();
            responseJson.put("jobName", projName + "-pipe");
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
	
    void makeJob() throws IOException {
        log.info("Beginning makeJob method");
        ProcessBuilder pBuilder = new ProcessBuilder();
        String cmd = "curl -X POST -u " + jenkinsAuth + " " + jenkinsUrl + "/job/seed/buildWithParameters --data githubURL=" + repoUrl + " --data projectName=" + projName + " --data slackChannel=" + slackChannel;
        pBuilder.command("sh", "-c", cmd);
        Process process = pBuilder.start();
        
        int exitCode = 1;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            log.error("Exception occurred within makeJob method.  " + e.getMessage());
            throw new IOException(e.getMessage());
        }

        if (exitCode != 0) {
            log.error("makeJob method failing due to cURL failure, exitCode!=0");
            throw new IOException("cURL failed");
        }
	}
}
