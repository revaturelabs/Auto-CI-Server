package com.revature.model.Spinnaker;

public class SpinnakerServiceObject {

    private String gitUri;
    private String cloudProviders;
    private String email;
    private String projectName;
    private String branch;

    public SpinnakerServiceObject() {
    }

    public SpinnakerServiceObject(String gitUri, String cloudProviders, String email, String projectName,
            String branch) {
        this.gitUri = gitUri;
        this.cloudProviders = cloudProviders;
        this.email = email;
        this.projectName = projectName;
        this.branch = branch;
    }

    public String getGitUri() {
        return gitUri;
    }

    public void setGitUri(String gitUri) {
        this.gitUri = gitUri;
    }

    public String getCloudProviders() {
        return cloudProviders;
    }

    public void setCloudProviders(String cloudProviders) {
        this.cloudProviders = cloudProviders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}