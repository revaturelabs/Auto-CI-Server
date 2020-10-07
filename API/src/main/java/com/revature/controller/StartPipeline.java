package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.ProgressResp;
import com.revature.model.Configuration.Configuration;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendReq;
import com.revature.model.Initialization.InitializationResp;
import com.revature.model.Jenkins.JenkinsServiceObject;
import com.revature.model.Jenkins.JenkinsServiceResp;
import com.revature.model.Spinnaker.SpinnakerServiceObject;
import com.revature.model.Spinnaker.SpinnakerServiceResp;

public class StartPipeline {

    public ProgressResp init(FrontendReq frontEndObj) {

        // to keep track of progress
        ProgressSingleton progress = ProgressSingleton.instance();

        // 1
        progress.setConfiguration("started");
        Configuration configObj = new Configuration();
        configObj.setGithubUsername(frontEndObj.getGithubUsername());
        configObj.setJenkinsURL("http://af8326665ef674d8badfbcfaf654ba6d-1042984266.us-east-1.elb.amazonaws.com:8080");
        configObj.setProjectName(frontEndObj.getMavenData().getProjectName());
        configObj.setGenerateGithubActions(true);
        configObj.setDebug(true);
        Config configController = new Config();
        ConfigurationResp configResp = configController.ConfigService(configObj);
        System.out.println("\nrunning config: finished");
        progress.setConfiguration("finished");

        // 2
        progress.setInitialization("started");
        InitializationController ic = new InitializationController();
        frontEndObj.setGithubURL(configResp.getGithubURL());
        System.out.println("the giturl returned: " + frontEndObj.getGithubURL());
        InitializationResp initResp = ic.runInitialization(frontEndObj);
        System.out.println("\nrunning init: finished");
        progress.setInitialization("finished");

        // 3
        progress.setJenkins("started");
        JenkinsServiceObject jenkinsServiceObject = new JenkinsServiceObject();
        jenkinsServiceObject.setGithubURL(configResp.getGithubURL());
        jenkinsServiceObject
                .setJenkinsURL("http://af8326665ef674d8badfbcfaf654ba6d-1042984266.us-east-1.elb.amazonaws.com:8080");
        jenkinsServiceObject.setProjectName(frontEndObj.getMavenData().getProjectName());
        jenkinsServiceObject.setSlackChannel("");
        Jenkins jenController = new Jenkins();
        JenkinsServiceResp jenkinsResp = jenController.JenkinsService(jenkinsServiceObject);
        System.out.println("\nrunning jenkins: finished");
        progress.setJenkins("finished");

        // 4
        progress.setSpinnaker("started");
        SpinnakerServiceObject spinnObj = new SpinnakerServiceObject();
        spinnObj.setGitUri(configResp.getGithubURL());
        List<String> listProviders = new ArrayList<>();
        listProviders.add("kubernetes");
        listProviders.add("AWS");
        spinnObj.setCloudProviders(listProviders);
        spinnObj.setEmail("testemail");
        spinnObj.setProjectName(frontEndObj.getMavenData().getProjectName());
        spinnObj.setBranch("main");

        // pretty print test
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json;
        try {
            json = mapper.writeValueAsString(spinnObj);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    
        SpinnakerController spinnController = new SpinnakerController();
        SpinnakerServiceResp spinnResp = spinnController.testSpinnaker(spinnObj);
        System.out.println("\nrunning spinn: finished");
        progress.setSpinnaker("finished" + spinnResp.getApplicationCreated());
        progress.setSpinnaker("finished" + spinnResp.getPipelineCreated());

        //we have finshed
        progress.setRunningStatus(false);

        return new ProgressResp("finished running", true);
    } 
}
