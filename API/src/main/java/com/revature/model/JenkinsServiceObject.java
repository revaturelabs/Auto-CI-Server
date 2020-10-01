package com.revature.model;

public class JenkinsServiceObject {

    private String jenkinsURL;
    private String githubURL;
    private String projectName;
    private String slackChannel;

    public JenkinsServiceObject() { }

    public JenkinsServiceObject(String jenkinsURL, String githubURL, String projectName, String slackChannel) {
        this.jenkinsURL = jenkinsURL;
        this.githubURL = githubURL;
        this.projectName = projectName;
        this.slackChannel = slackChannel;
    }

    public String getJenkinsURL() {
        return jenkinsURL;
    }

    public void setJenkinsURL(String jenkinsURL) {
        this.jenkinsURL = jenkinsURL;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSlackChannel() {
        return slackChannel;
    }

    public void setSlackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
    }

}