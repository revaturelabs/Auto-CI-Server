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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigServlet extends HttpServlet {
    final String ORG_NAME = "revaturelabs";
    final String DBG_ORG_NAME = "alxllabs";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    GitHubAPI github = new GitHubAPI();
    String gitUsername;
    String jenkinsUri = "";
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
                
                responseJson.put("githubURL", ghRepo.getHttpTransportUrl());
                responseJson.put("madeHook", madeHook);
            }
            log.info("post actions for ConfigServlet successful");
        } catch (Exception e) {
            log.error(e.getMessage());
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
            log.info("Request read for parsing to Json in ConfigServlet");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException("Error reading input!");
        }

        JSONObject json = null;
        try {
            json = new JSONObject(jb.toString());
            log.info("JSONObject created in ConfigServlet");
        } catch (JSONException e) {
            log.error(e.getMessage());
            String err = "Request string is not JSON: " + jb.toString();
            throw new IOException(err);
        }
        return json;
    }

    protected void parseJsonToVars(JSONObject json) throws IOException {
        try {
            gitUsername = json.getString("githubUsername");
            projName = json.getString("projectName");
            usingJenkins = json.getBoolean("makeJenkinsWebhook");
            if (usingJenkins) {
                jenkinsUri = json.getString("jenkinsURL");
            }
            github.debugMode = json.has("debug") && json.getBoolean("debug");
            log.info("Json successfully parsed in ConfigServlet");
        } catch (JSONException e) {
            log.error(e.getMessage());
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
            log.info("GHRepository object successfully created in ConfigServlet");

            return repo;
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private boolean createWebhook(GHRepository repo) throws IOException {
        List<GHEvent> events = new ArrayList<GHEvent>();
        events.add(GHEvent.PUSH);
        try {
            URL ju = new URL(jenkinsUri + "github-webhook/");
            repo.createWebHook(ju, events);
            log.info("Webhook successfully created in ConfigServlet");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}