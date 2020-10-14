package com.revature.model.Progress;

public class ProgressObj {

    private Boolean runningStatus;
    private String configuration;
    private String initialization;
    private String jenkins;
    private String Spinnaker;

    public Boolean getRunningStatus() {
        return this.runningStatus;
    }

    public void setRunningStatus(Boolean runningStatus) {
        this.runningStatus = runningStatus;
    }

    public String getInitialization() {
        return this.initialization;
    }

    public void setInitialization(String initialization) {
        this.initialization = initialization;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getJenkins() {
        return this.jenkins;
    }

    public void setJenkins(String jenkins) {
        this.jenkins = jenkins;
    }

    public String getSpinnaker() {
        return this.Spinnaker;
    }

    public void setSpinnaker(String Spinnaker) {
        this.Spinnaker = Spinnaker;
    }

    public ProgressObj(){}

    

}
