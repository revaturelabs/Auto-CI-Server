package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/configure")
public class ConfigServlet extends HttpServlet {
    GitHubAPI github = new GitHubAPI();
    String gitUsername;
    String jenkinsUri;
    String projName;
    boolean usingGHActions;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        parseParams(request);

        String ghRepoUri = createRepo();

        if (usingGHActions) {
            createGHActions();
        } else {
            createWebhook();
        }

        response.getWriter().write(ghRepoUri);
    }

    private void parseParams(HttpServletRequest req) {
        gitUsername = "gitUser";
        jenkinsUri = "jenkinsUri";
        projName = "Project Name";
        usingGHActions = false;
    }

    private String createRepo() {

        return "repo_uri";
    }

    private void createWebhook() {

    }

    private void createGHActions() {

    }
}