package com.revature.model.Frontend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.revature.model.Partials.MvnDataObj;
import com.revature.model.Partials.NpmDataObj;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "githubUsername", "githubURL", "ide", "generateGithubActions", "isMaven", "mavenData", "npmData" })
public class FrontendReq {

    private String githubUsername;
    private String githubURL;
    private String ide;
    private Boolean generateGithubActions;
    private Boolean isMaven;

    private MvnDataObj mavenData;
    private NpmDataObj npmData;

    public FrontendReq() { }

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

    public Boolean getGenerateGithubActions() {
        return this.generateGithubActions;
    }

    public void setGenerateGithubActions(Boolean generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
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

    public FrontendReq githubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
        return this;
    }

    public FrontendReq githubURL(String githubURL) {
        this.githubURL = githubURL;
        return this;
    }

    public FrontendReq ide(String ide) {
        this.ide = ide;
        return this;
    }

    public FrontendReq generateGithubActions(Boolean generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
        return this;
    }

    public FrontendReq isMaven(Boolean isMaven) {
        this.isMaven = isMaven;
        return this;
    }

    public FrontendReq mavenData(MvnDataObj mavenData) {
        this.mavenData = mavenData;
        return this;
    }

    public FrontendReq npmData(NpmDataObj npmData) {
        this.npmData = npmData;
        return this;
    }

}