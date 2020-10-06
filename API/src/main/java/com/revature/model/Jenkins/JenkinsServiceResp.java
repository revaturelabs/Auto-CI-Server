package com.revature.model.Jenkins;

public class JenkinsServiceResp {
    private String devJob;
    private String prodJob;

    public JenkinsServiceResp() {
    }

    public String getDevJob() {
        return this.devJob;
    }

    public void setDevJob(String devJob) {
        this.devJob = devJob;
    }

    public String getProdJob() {
        return this.prodJob;
    }

    public void setProdJob(String prodJob) {
        this.prodJob = prodJob;
    }

    public JenkinsServiceResp devJob(String devJob) {
        this.devJob = devJob;
        return this;
    }

    public JenkinsServiceResp prodJob(String prodJob) {
        this.prodJob = prodJob;
        return this;
    }

}
