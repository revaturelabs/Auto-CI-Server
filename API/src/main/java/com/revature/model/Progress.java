package com.revature.model;

public class Progress {

    //this class is to keep track of all the stages progess in the pipeline

    private String frontend;
    private String initialization;
    private String configuration;
    private String jenkins;
    private String Spinnaker;

    public Progress(){
        this.frontend = "started";
        this.initialization = "not done";
        this.configuration = "not done";
        this.jenkins = "not done";
        this.Spinnaker = "not done";
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
