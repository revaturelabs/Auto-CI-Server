package com.revature.model.Frontend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.revature.model.Partials.MvnDataObj;
import com.revature.model.Partials.NpmDataObj;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "githubUsername", "githubURL", "ide", "makeJenkinsWebhook", "isMaven", "isAzure", "mavenData", "npmData", "slackChannel" })
public class FrontendObj {

    private String githubUsername;
    private String githubURL;
    private String ide;
    private Boolean makeJenkinsWebhook;
    private Boolean isMaven;
    private Boolean isAzure;
    private String slackChannel;
    private MvnDataObj mavenData;
    private NpmDataObj npmData;

    public FrontendObj() { }

    public String getGithubUsername() {
        return this.githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getGithubURL() {
        return this.githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getIde() {
        return this.ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public Boolean getMakeJenkinsWebhook() {
        return this.makeJenkinsWebhook;
    }

    public void setMakeJenkinsWebhook(Boolean makeJenkinsWebhook) {
        this.makeJenkinsWebhook = makeJenkinsWebhook;
    }

    public Boolean getIsMaven() {
        return this.isMaven;
    }

    public void setIsMaven(Boolean isMaven) {
        this.isMaven = isMaven;
    }

    public MvnDataObj getMavenData() {
        return this.mavenData;
    }

    public void setMavenData(MvnDataObj mavenData) {
        this.mavenData = mavenData;
    }

    public NpmDataObj getNpmData() {
        return this.npmData;
    }

    public void setNpmData(NpmDataObj npmData) {
        this.npmData = npmData;
    }

    public FrontendObj githubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
        return this;
    }

    public FrontendObj githubURL(String githubURL) {
        this.githubURL = githubURL;
        return this;
    }

    public FrontendObj ide(String ide) {
        this.ide = ide;
        return this;
    }

    public FrontendObj makeJenkinsWebhook(Boolean makeJenkinsWebhook) {
        this.makeJenkinsWebhook = makeJenkinsWebhook;
        return this;
    }

    public FrontendObj isMaven(Boolean isMaven) {
        this.isMaven = isMaven;
        return this;
    }

    public FrontendObj mavenData(MvnDataObj mavenData) {
        this.mavenData = mavenData;
        return this;
    }

    public FrontendObj npmData(NpmDataObj npmData) {
        this.npmData = npmData;
        return this;
    }

    public Boolean getIsAzure() {
        return this.isAzure;
    }

    public void setIsAzure(Boolean isAzure) {
        this.isAzure = isAzure;
    }

    public String getSlackChannel() {
        return this.slackChannel;
    }

    public void setSlackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
    }

}