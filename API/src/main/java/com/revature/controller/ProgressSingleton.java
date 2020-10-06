package com.revature.controller;


/**
 * this method sets current progress object; It will be deleted once the pipeline it complete;
 */

public class ProgressSingleton {

    private Boolean runningStatus = false;
    private String frontend;
    private String initialization;
    private String configuration;
    private String jenkins;
    private String Spinnaker;

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

    public String getFrontend() {
        return this.frontend;
    }

    public void setFrontend(String frontend) {
        this.frontend = frontend;
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
}