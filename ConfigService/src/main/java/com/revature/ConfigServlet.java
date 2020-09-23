package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GHOrganization.Permission;

public class ConfigServlet extends HttpServlet {
    final String ORG_NAME = "TestTestOrgTestTest";

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

            response.getWriter().write(ghRepo.getHttpTransportUrl());
        }
    }

    private void parseParams(HttpServletRequest req) {
        gitUsername = "ItsAlxl";
        jenkinsUri = "jenkinsUri";
        projName = "Test Project Name";
        usingGHActions = false;
    }

    private GHRepository createRepo() throws IOException {
        github.getInstance().getOrganization(ORG_NAME).createRepository(projName).create();
        return null;
    }

    private void createWebhook(GHRepository repo) {

    }

    private void createGHActions(GHRepository repo) {

    }

    public static void main(String[] args) throws IOException {
        GitHubAPI git = new GitHubAPI();
        git.getInstance().getOrganization("TestTestOrgTestTest").createRepository("Test Proj").autoInit(true).create().addCollaborators(Permission.ADMIN, git.getInstance().getUser("ItsAlxl"));
    }
}