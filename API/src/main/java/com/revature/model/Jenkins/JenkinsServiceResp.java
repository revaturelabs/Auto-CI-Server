package com.revature.model.Jenkins;

public class JenkinsServiceResp {
    private String jobName;

    public JenkinsServiceResp() { }

    public JenkinsServiceResp(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
