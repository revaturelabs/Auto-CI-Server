package com.revature.model;

public class SpinnakerService {

    private String repoURL;
    private String jobName; // Name of the job made in JenkinsService
    private String projMetadata;

    public SpinnakerService() {
    }

    public SpinnakerService(String repoURL, String jobName, String projMetadata) {
        this.repoURL = repoURL;
        this.jobName = jobName;
        this.projMetadata = projMetadata;
    }

    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getProjMetadata() {
        return projMetadata;
    }

    public void setProjMetadata(String projMetadata) {
        this.projMetadata = projMetadata;
    }
}