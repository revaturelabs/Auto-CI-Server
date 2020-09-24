package com.revature;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHOrganization.Permission;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;

public class ConfigServlet extends HttpServlet {
    final String ORG_NAME = "revaturelabs";

    GitHubAPI github = new GitHubAPI();
    String gitUsername;
    String jenkinsUri;
    String projName;
    boolean usingGHActions;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        parseParams(request);

        GHRepository ghRepo = createRepo();
        if (ghRepo == null) {
            response.getWriter().write("Error connecting to GitHub");
        } else {
            if (usingGHActions) {
                createGHActions(ghRepo);
            } else {
                createWebhook(ghRepo);
            }

            response.setContentType("application/json");
            response.getWriter().write("{repoUrl: \"" + ghRepo.getHttpTransportUrl() + "\"}");
        }
    }

    private void parseParams(HttpServletRequest req) throws IOException {
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
        try {
            gitUsername = json.getString("gitUser");
            jenkinsUri = json.getString("jenkinsUrl");
            projName = json.getString("projName");
            usingGHActions = !json.getBoolean("useJenkins");
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

            GHOrganization org = github.getInstance().getOrganization(ORG_NAME);
            GHRepository repo = org.createRepository(projName).autoInit(true).create();
            repo.addCollaborators(Permission.ADMIN, user);

            return repo;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void createWebhook(GHRepository repo) {

    }

    private void createGHActions(GHRepository repo) {

    }
}