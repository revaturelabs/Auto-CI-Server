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

    

}
