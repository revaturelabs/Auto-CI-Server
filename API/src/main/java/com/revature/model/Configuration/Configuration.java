package com.revature.model.Configuration;

public class Configuration {

    private String githubUsername;
    private String jenkinsURL;
    private String projectName;
    private Boolean generateGithubActions; // Was Jenkins specified, or are we using Github Actions?
    private Boolean debug; // Optional; leave to true to prevent polluting the revaturelabs org with our test repos


    public Configuration() {
    }

    public String getGithubUsername() {
        return this.githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getJenkinsURL() {
        return this.jenkinsURL;
    }

    public void setJenkinsURL(String jenkinsURL) {
        this.jenkinsURL = jenkinsURL;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean getGenerateGithubActions() {
        return this.generateGithubActions;
    }

    public void setGenerateGithubActions(Boolean generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
    }

    public Boolean getDebug() {
        return this.debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Configuration githubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
        return this;
    }

    public Configuration jenkinsURL(String jenkinsURL) {
        this.jenkinsURL = jenkinsURL;
        return this;
    }

    public Configuration projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Configuration generateGithubActions(Boolean generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
        return this;
    }

    public Configuration debug(Boolean debug) {
        this.debug = debug;
        return this;
    }
   
}