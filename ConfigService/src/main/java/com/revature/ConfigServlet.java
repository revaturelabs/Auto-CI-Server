package com.revature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

@WebServlet("/configure")
public class ConfigServlet extends HttpServlet {
    GitHub github;

    String gitUsername;
    String jenkinsUri;
    String projName;
    boolean usingGHActions;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        githubLogin("auth_token");

        parseParams();

        String ghRepoUri = createRepo();

        if (usingGHActions) {
            createGHActions();
        } else {
            createWebhook();
        }

        response.getWriter().write(ghRepoUri);
    }

    private void githubLogin(String token) throws IOException {
        github = new GitHubBuilder().withOAuthToken(token).build();
    }

    private void parseParams() {
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