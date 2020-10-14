package com.revature.model.Jenkins;


public class JenkinsServiceResp {
    private String devJob;
    private String prodJob;
    private String errorMsg;


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

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public JenkinsServiceResp devJob(String devJob) {
        this.devJob = devJob;
        return this;
    }

    public JenkinsServiceResp prodJob(String prodJob) {
        this.prodJob = prodJob;
        return this;
    }

    public JenkinsServiceResp errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

}
