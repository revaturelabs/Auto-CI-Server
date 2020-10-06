package com.revature.controller;

import com.revature.model.Configuration.Configuration;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendReq;
import com.revature.model.Initialization.InitializationResp;
import com.revature.model.Jenkins.JenkinsServiceObject;
import com.revature.model.Jenkins.JenkinsServiceResp;
import com.revature.model.Spinnaker.SpinnakerServiceObject;
import com.revature.model.Spinnaker.SpinnakerServiceResp;

/**
 * this method sets current progress object; It will be deleted once the
 * pipeline it complete;
 */

public class ProgressSingleton {

    private Boolean runningStatus = false;
    private String frontend;
    private String initialization;
    private String configuration;
    private String jenkins;
    private String spinnaker;

    private static ProgressSingleton single_instance = null;

    private ProgressSingleton(){}

    public static ProgressSingleton instance() 
    { 
        if (single_instance == null) {
            single_instance = new ProgressSingleton(); 
        }
            
        return single_instance; 
    }

    public void startInit(FrontendReq frontEndObj){

        //1
        Configuration configObj = new Configuration();
        configObj.setGithubUsername(frontEndObj.getGithubUsername());
        configObj.setJenkinsURL("http://a740e512b731f442aa6fa2f96321715a-1223789559.us-east-1.elb.amazonaws.com:8080/");
        Config configController = new Config();
        ConfigurationResp configResp = configController.ConfigService(configObj);
        System.out.println("\nrunning config: finished");

        //2
        InitializationController ic = new InitializationController();
        frontEndObj.setGithubURL(configResp.getGithubURL());
        InitializationResp initResp = ic.runInitialization(frontEndObj);
        System.out.println("\nrunning init: finished");

        //3
        JenkinsServiceObject jenkinsServiceObject = new JenkinsServiceObject();
        jenkinsServiceObject.setGithubURL(configResp.getGithubURL());
        jenkinsServiceObject.setJenkinsURL("http://a740e512b731f442aa6fa2f96321715a-1223789559.us-east-1.elb.amazonaws.com:8080/");
        jenkinsServiceObject.setProjectName("demo");
        jenkinsServiceObject.setSlackChannel("");
        Jenkins jenController = new Jenkins();
        JenkinsServiceResp jenkinsResp = jenController.JenkinsService(jenkinsServiceObject);
        System.out.println("\nrunning jenkins: finished");

        //4
        SpinnakerServiceObject spinnObj = new SpinnakerServiceObject();
        spinnObj.setGitUri(configResp.getGithubURL());
        spinnObj.setCloudProviders("asure");
        spinnObj.setEmail("true");
        spinnObj.setProjectName("visualstudiocode");
        spinnObj.setBranch("false");
        SpinnakerController spinnController = new SpinnakerController();
        SpinnakerServiceResp spinnResp = spinnController.testSpinnaker(spinnObj);
        System.out.println("\nrunning spinn: finished");

        //we have finshed
        runningStatus = false;
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
        return this.spinnaker;
    }

    public void setSpinnaker(String spinnaker) {
        this.spinnaker = spinnaker;
    }
}