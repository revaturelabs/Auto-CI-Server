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
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
			makeJob();
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
        } catch (JSONException e) {
            String err = "Error parsing JSON request string";
            if (json != null) {
                err += ": " + json.toString();
            }
            throw new IOException(err);
        }
    }
	
    private void makeJob() throws IOException {
		
	}
}