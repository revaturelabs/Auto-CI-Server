package com.revature.model;

public class JenkinsService {

    private String repoURL;
    private String jenkinsURI;
    private String projMetadata;

    public JenkinsService() { }

    public JenkinsService(String repoURL, String jenkinsURI, String projMetadata) {
        this.repoURL = repoURL;
        this.jenkinsURI = jenkinsURI;
        this.projMetadata = projMetadata;
    }

    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }

    public String getJenkinsURI() {
        return jenkinsURI;
    }

    public void setJenkinsURI(String jenkinsURI) {
        this.jenkinsURI = jenkinsURI;
    }

    public String getProjMetadata() {
        return projMetadata;
    }

    public void setProjMetadata(String projMetadata) {
        this.projMetadata = projMetadata;
    }
}