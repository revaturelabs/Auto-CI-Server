package com.revature.controller;

/**
 * this method sets current progress object; It will be deleted once the
 * pipeline it complete;
 */

public class ProgressSingleton {

    private Boolean runningStatus = false;
    private String initialization = "not started";
    private String configuration = "not started";
    private String jenkins = "not started";
    private String spinnaker = "not started";

    private static ProgressSingleton single_instance = null;

    private ProgressSingleton(){}

    public static ProgressSingleton instance() 
    { 
        if (single_instance == null) {
            single_instance = new ProgressSingleton(); 
        }
            
        return single_instance; 
    }

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
        return this.spinnaker;
    }

    public void setSpinnaker(String spinnaker) {
        this.spinnaker = spinnaker;
    }
}