package com.revature;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class JenkinsServlet extends HttpServlet {
    String repoUrl;
    String projName;
    String slackChannel;
    String jenkinsUrl = "";
  
    final String jenkinsAuth = "user:token";
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            makeJob();
            responseJson.put("jobName", projName + "-Pipe");
        } catch (Exception e) {
            responseJson.put("errorMsg", e.getMessage());
        }
		
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toString(0));
    }

    private void parseParams(HttpServletRequest req) throws IOException {
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
            throw new IOException("Error reading input!");
        }

        JSONObject json = null;
        try {
            json = new JSONObject(jb.toString());
        } catch (JSONException e) {
            String err = "Request string is not JSON: " + jb.toString();
            throw new IOException(err);
        }
        return json;
    }

    private void parseJsonToVars(JSONObject json) throws IOException {
        try {
            repoUrl = json.getString("repoUrl");
            projName = json.getString("projName");
            slackChannel = json.getString("slackChannel");
            jenkinsUrl = json.getString("jenkinsUrl");
        } catch (JSONException e) {
            String err = "Error parsing JSON request string";
            if (json != null) {
                err += ": " + json.toString();
            }
            throw new IOException(err);
        }
    }
	
    void makeJob() throws IOException {
        ProcessBuilder pBuilder = new ProcessBuilder();
        String cmd = "curl -X POST -u " + jenkinsAuth + " " + jenkinsUrl + "/job/seed/buildWithParameters --data githubURL=" + repoUrl + " --data projectName=" + projName + " --data slackChannel=" + slackChannel;
        pBuilder.command("sh", "-c", cmd);
        Process process = pBuilder.start();
        
        int exitCode = 1;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }

        if (exitCode != 0) {
            throw new IOException("cURL failed");
        }
	}
}