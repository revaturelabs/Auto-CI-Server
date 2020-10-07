package com.revature.model.Spinnaker;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "gitUri", "cloudProviders", "email", "projectName", "branch" })
public class SpinnakerServiceObject {

    private String gitUri;
    private List<String> cloudProviders = null;;
    private String email;
    private String projectName;
    private String branch;

    public String getGitUri() {
        return this.gitUri;
    }

    public void setGitUri(String gitUri) {
        this.gitUri = gitUri;
    }

    public List<String> getCloudProviders() {
        return this.cloudProviders;
    }

    public void setCloudProviders(List<String> cloudProviders) {
        this.cloudProviders = cloudProviders;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public SpinnakerServiceObject() {
    }

}