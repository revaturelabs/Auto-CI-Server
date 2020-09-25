package com.revature;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import org.kohsuke.github.GHEvent;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHOrganization.Permission;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;

public class ConfigServlet extends HttpServlet {
    final String ORG_NAME = "revaturelabs";
    final String DBG_ORG_NAME = "alxllabs";

    GitHubAPI github = new GitHubAPI();
    String gitUsername;
    String jenkinsUri;
    String projName;
    boolean usingJenkins;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            GHRepository ghRepo = createRepo();
            if (ghRepo == null) {
                responseJson.put("errorMsg", "Couldn't connect to GitHub");
            } else {
                boolean madeHook = false;
                if (usingJenkins) {
                    madeHook = createWebhook(ghRepo);
                }
                
                responseJson.put("repoUrl", ghRepo.getHttpTransportUrl());
                responseJson.put("madeHook", madeHook);
            }
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
            gitUsername = json.getString("gitUser");
            jenkinsUri = json.getString("jenkinsUrl");
            projName = json.getString("projName");
            usingJenkins = json.getBoolean("useJenkins");
            if (json.has("debug") && !json.getBoolean("debug")) {
                github.debugMode = false;
            }
        } catch (JSONException e) {
            String err = "Error parsing JSON request string";
            if (json != null) {
                err += ": " + json.toString();
            }
            throw new IOException(err);
        }
    }

    private GHRepository createRepo() throws IOException {
        try {
            GHUser user = github.getInstance().getUser(gitUsername);

            String orgName = ORG_NAME;
            if (github.debugMode) {
                orgName = DBG_ORG_NAME;
            }

            GHOrganization org = github.getInstance().getOrganization(orgName);
            GHRepository repo = org.createRepository(projName).autoInit(true).create();
            repo.addCollaborators(Permission.ADMIN, user);

            return repo;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private boolean createWebhook(GHRepository repo) throws IOException {
        List<GHEvent> events = new ArrayList<GHEvent>();
        events.add(GHEvent.PUSH);
        try {
            URL ju = new URL(jenkinsUri);
            repo.createWebHook(ju, events);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}