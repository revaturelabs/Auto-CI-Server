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

public class Azure extends HttpServlet {
    String repoUrl;
    String projName;
    String gitUrl;
    String slackChannel;
  
    final String azureAuth = System.getenv("AZURE_AUTHENTICATION");
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Azure servlet started.");
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            log.info("Logging into Azure...");
			azureLogin(responseJson);
            log.info("Making project...");
            makeProject(responseJson);
            log.info("Making pipeline...");
            makePipeline(responseJson);
            log.info("Making container repo...");
            makeContainerRepo(responseJson);
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
            projName = json.getString("projectName").toLowerCase();
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
	
    private void azureLogin(JSONObject response) throws IOException {
		
    }
	
    private void makeProject(JSONObject response) throws IOException {
		
    }
	
    private void makePipeline(JSONObject response) throws IOException {
		
    }
    
    private void makeContainerRepo(JSONObject response) throws IOException {
		
    }
}
