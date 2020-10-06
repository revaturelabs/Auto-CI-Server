package com.revature.model.Initialization;

import com.revature.model.Frontend.MvnDataObj;
import com.revature.model.Frontend.NpmDataObj;

public class InitializationObj {

    private String githubUsername;
    private String githubURL;
    private String ide;
    private String generateGithubActions;
    private String isMaven;

    private MvnDataObj mavenData;
    private NpmDataObj npmData;

    public InitializationObj() { }

    // if isMaven == true
    public InitializationObj(String githubUsername, String githubURL, String ide, String generateGithubActions,
            String isMaven, MvnDataObj mavenData) {
        this.githubUsername = githubUsername;
        this.githubURL = githubURL;
        this.ide = ide;
        this.generateGithubActions = generateGithubActions;
        this.isMaven = isMaven;
        this.mavenData = mavenData;
    }

    // if isMaven == false
    public InitializationObj(String githubUsername, String githubURL, String ide, String generateGithubActions,
            String isMaven, NpmDataObj npmData) {
        this.githubUsername = githubUsername;
        this.githubURL = githubURL;
        this.ide = ide;
        this.generateGithubActions = generateGithubActions;
        this.isMaven = isMaven;
        this.npmData = npmData;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getGithubURL() {
        return githubURL;
    }

    public void setGithubURL(String githubURL) {
        this.githubURL = githubURL;
    }

    public String getide() {
        return ide;
    }

    public void setide(String ide) {
        this.ide = ide;
    }

    public String isGenerateGithubActions() {
        return generateGithubActions;
    }

    public void setGenerateGithubActions(String generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
    }

    public String isMaven() {
        return isMaven;
    }

    public void setMaven(String isMaven) {
        this.isMaven = isMaven;
    }

    public MvnDataObj getMavenData() {
        return mavenData;
    }

    public void setMavenData(MvnDataObj mavenData) {
        this.mavenData = mavenData;
    }

    public NpmDataObj getNpmData() {
        return npmData;
    }

    public void setNpmData(NpmDataObj npmData) {
        this.npmData = npmData;
    }

}