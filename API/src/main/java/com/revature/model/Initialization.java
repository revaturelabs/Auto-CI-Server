package com.revature.model;

public class Initialization {

    private String githubUsername;
    private String githubURL;
    private String IDE;
    private boolean generateGithubActions;
    private boolean isMaven;

    private MvnDataObj mavenData;
    private NpmDataObj npmData;

    public Initialization() { }

    // if isMaven == true
    public Initialization(String githubUsername, String githubURL, String IDE, boolean generateGithubActions,
            boolean isMaven, MvnDataObj mavenData) {
        this.githubUsername = githubUsername;
        this.githubURL = githubURL;
        this.IDE = IDE;
        this.generateGithubActions = generateGithubActions;
        this.isMaven = isMaven;
        this.mavenData = mavenData;
    }

    // if isMaven == false
    public Initialization(String githubUsername, String githubURL, String iDE, boolean generateGithubActions,
            boolean isMaven, NpmDataObj npmData) {
        this.githubUsername = githubUsername;
        this.githubURL = githubURL;
        IDE = iDE;
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

    public String getIDE() {
        return IDE;
    }

    public void setIDE(String iDE) {
        IDE = iDE;
    }

    public boolean isGenerateGithubActions() {
        return generateGithubActions;
    }

    public void setGenerateGithubActions(boolean generateGithubActions) {
        this.generateGithubActions = generateGithubActions;
    }

    public boolean isMaven() {
        return isMaven;
    }

    public void setMaven(boolean isMaven) {
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