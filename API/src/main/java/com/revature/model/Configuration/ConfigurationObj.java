package com.revature.model.Configuration;

public class ConfigurationObj {

    private String githubUsername;
    private String jenkinsURL;
    private String projectName;
    private Boolean makeJenkinsWebhook; // Was Jenkins specified, or are we using Github Actions?
    private Boolean debug; // Optional; leave to true to prevent polluting the revaturelabs org with our test repos


    public ConfigurationObj() {
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

    public Boolean getMakeJenkinsWebhook() {
        return this.makeJenkinsWebhook;
    }

    public void setMakeJenkinsWebhook(Boolean makeJenkinsWebhook) {
        this.makeJenkinsWebhook = makeJenkinsWebhook;
    }

    public Boolean getDebug() {
        return this.debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public ConfigurationObj githubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
        return this;
    }

    public ConfigurationObj jenkinsURL(String jenkinsURL) {
        this.jenkinsURL = jenkinsURL;
        return this;
    }

    public ConfigurationObj projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public ConfigurationObj makeJenkinsWebhook(Boolean makeJenkinsWebhook) {
        this.makeJenkinsWebhook = makeJenkinsWebhook;
        return this;
    }

    public ConfigurationObj debug(Boolean debug) {
        this.debug = debug;
        return this;
    }
   
}