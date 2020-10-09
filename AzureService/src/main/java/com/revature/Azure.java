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

public class Azure extends HttpServlet {
    String repoUrl;
    String projName;
    String gitUrl;
    String slackChannel;
  
    final String azureAuth = " -u " + System.getenv("AZURE_USERNAME") + " -p " + System.getenv("AZURE_PASSWORD");
    final String azOrgName = System.getenv("AZURE_ORG_NAME");
    final String azServiceId = System.getenv("AZURE_GITHUB_AUTH_SERVICE_ID");
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Azure servlet started.");
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            CommandExecutor cmd = new CommandExecutor();

            log.info("Logging into Azure...");
			azureLogin(cmd, responseJson);
            log.info("Making pipeline...");
            makePipeline(cmd, responseJson);
            log.info("Making container repo...");
            makeContainerRepo(cmd, responseJson);
        } catch (Exception e) {
            responseJson.put("errorMsg", e.getMessage());
            log.error(e.getMessage());
        }
		
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toString(0));
        log.info("Azure servlet has written HTTP response and finished.");
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
            projName = json.getString("projectName");
            slackChannel = json.getString("slackChannel");
            gitUrl = json.getString("githubURL");
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

    private void execAndLogCmd(CommandExecutor cmd, String command) {
        String execOutput = cmd.execute(command);
        if (cmd.wasLastCmdSuccess()) {
            log.info(execOutput);
        } else {
            log.error(execOutput);
        }
        System.out.println(execOutput);
    }
	
    private void azureLogin(CommandExecutor cmd, JSONObject response) throws IOException {
        execAndLogCmd(cmd, "az login" + azureAuth);
        execAndLogCmd(cmd, "az devops configure -d organization=https://dev.azure.com/RevatureProjectFactory project=ProjectFactory");
    }
    
    private String makePipelineCommand(String branch) {
        return "az pipelines create --folder " + projName + " --name " + projName + "-" + branch + " --repository " + gitUrl + " --branch " + branch + " --yaml-path azure-pipeline.yaml --service-connection " + azServiceId;
    }
    private void makePipeline(CommandExecutor cmd, JSONObject response) throws IOException {
        execAndLogCmd(cmd, "az pipelines folder create --path " + projName);
        execAndLogCmd(cmd, makePipelineCommand("dev"));
        execAndLogCmd(cmd, makePipelineCommand("prod"));
    }
    
    private void makeContainerRepo(CommandExecutor cmd, JSONObject response) throws IOException {
		
    }
}
