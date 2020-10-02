package com.revature.model.Configuration;

public class Configuration {

    private String githubUsername;
    private String jenkinsURI;
    private String projectName;
    private boolean generateGithubActions; // Was Jenkins specified, or are we using Github Actions?
    private boolean debug; // Optional; leave to true to prevent polluting the revaturelabs org with our test repos

    public Configuration() { }

    public Configuration(String githubUsername, String jenkinsURI, String projectName, boolean generateGithubActions,
            boolean debug) {
        this.githubUsername = githubUsername;
        this.jenkinsURI = jenkinsURI;
        this.projectName = projectName;
        this.generateGithubActions = generateGithubActions;
        this.debug = debug;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getJenkinsURI() {
        return jenkinsURI;
    }

    public void setJenkinsURI(String jenkinsURI) {
        this.jenkinsURI = jenkinsURI;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isGenerateGithubActions() {
        return generateGithubActions;
    }

    public void setGenerateGithubActions(boolean generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
   
}