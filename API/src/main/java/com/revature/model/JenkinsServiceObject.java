package com.revature.model;

public class JenkinsServiceObject {

    private String repoURL;
    private String jenkinsURI;
    private String projMetadata;

    public JenkinsServiceObject() { }

    public JenkinsServiceObject(String repoURL, String jenkinsURI, String projMetadata) {
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

    @Override
    public String toString() {
        return "JenkinsService [jenkinsURI=" + jenkinsURI + ", projMetadata=" + projMetadata + ", repoURL=" + repoURL
                + "]";
    }
}