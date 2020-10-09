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

import okhttp3.Response;

public class StartPipeline <T> {

    private static String URLconfig = "http://aa7d4312010bb42048cb10bf1f6ff356-640074528.us-east-1.elb.amazonaws.com:8080/config/";
    private static String URLinit = "http://acefd179b3bf24f75a454ad47bab768e-575975828.us-east-1.elb.amazonaws.com/init/";
    private static String URLjenkins = "http://ace4f487a5b1c4ff986fa0bd92cbb63b-887864636.us-east-1.elb.amazonaws.com:8080/jenkins-svlt/";
    private static String URLjenkinshost = "http://af8326665ef674d8badfbcfaf654ba6d-1042984266.us-east-1.elb.amazonaws.com:8080";
    private static String URLspinnaker = "http://a0db1c05a05a74349818c2986d5b37be-2043532376.us-east-1.elb.amazonaws.com:8080/api/pipeline";

    public ProgressResp init(FrontendReq frontEndObj) {

        // to keep track of progress
        ProgressSingleton progress = ProgressSingleton.instance();

        aMasterController master = new aMasterController();
        ObjectMapper mapper = new ObjectMapper();

        // 1
        progress.setConfiguration("started");
        Configuration configObj = new Configuration();
        ConfigurationResp configResp;
        configObj.setGithubUsername(frontEndObj.getGithubUsername());
        configObj.setJenkinsURL(URLjenkinshost);
        configObj.setProjectName(frontEndObj.getMavenData().getProjectName());
        configObj.setGenerateGithubActions(true);
        configObj.setDebug(true);
        //Config configController = new Config();
        //ConfigurationResp configResp = configController.ConfigService(configObj);
        Response response1 = master.sendHttpReq(configObj, URLconfig);
        //Failure handling could go here, pass in response and the response type
        if (aFailureChecker.CheckCode(response1)) {
            configResp = mapper.readValue(response1.body().byteStream(), ConfigurationResp.class);
            progress.setConfiguration("finished");
        } else {
            progress.setConfiguration("failed");
            progress.setRunningStatus(false);
            configResp = new ConfigurationResp();
        }
        

        // 2
        progress.setInitialization("started");
        frontEndObj.setGithubURL(configResp.getGithubURL());
        InitializationResp initResp = new InitializationResp("not done");
        Response response2 = master.sendHttpReq(frontEndObj, URLinit);
        //Failure handling could go here, pass in response and the response type
        if (aFailureChecker.CheckCode(response2)) {
            //Is the line below even needed?  Do we do anything with that?
            initResp = mapper.readValue(response2.body().byteStream(), InitializationResp.class);
            progress.setInitialization("finished");
        } else {
            progress.setInitialization("failed");
            progress.setRunningStatus(false);
            //don't need another constructor here, already declared above
        }


        // 3
        progress.setJenkins("started");
        JenkinsServiceObject jenkinsServiceObject = new JenkinsServiceObject();
        JenkinsServiceResp jenkinsResp;
        jenkinsServiceObject.setGithubURL(configResp.getGithubURL());
        jenkinsServiceObject
                .setJenkinsURL(URLjenkinshost);
        jenkinsServiceObject.setProjectName(frontEndObj.getMavenData().getProjectName());
        jenkinsServiceObject.setSlackChannel("");
        Response response3 = master.sendHttpReq(jenkinsServiceObject, URLjenkins);
        //Failure handling could go here, pass in response and the response type
        if (aFailureChecker.CheckCode(response3)) {
            jenkinsResp = mapper.readValue(response3.body().byteStream(), JenkinsServiceResp.class);
            progress.setJenkins("finished");
        } else {
            progress.setJenkins("failed");
            progress.setRunningStatus(false);
            jenkinsResp = new JenkinsServiceResp();
        }


       


        // 4
        progress.setSpinnaker("started");
        SpinnakerServiceObject spinnObj = new SpinnakerServiceObject();
        SpinnakerServiceResp spinnResp = new SpinnakerServiceResp();
        spinnObj.setGitUri(configResp.getGithubURL());
        List<String> listProviders = new ArrayList<>();
        listProviders.add("kubernetes");
        listProviders.add("AWS");
        spinnObj.setCloudProviders(listProviders);
        spinnObj.setEmail("testemail");
        spinnObj.setProjectName(frontEndObj.getMavenData().getProjectName());
        spinnObj.setBranch("main");

        // pretty print test
        // ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        // String json;
        // try {
        //     json = mapper.writeValueAsString(spinnObj);
        //     System.out.println(json);
        // } catch (JsonProcessingException e) {
        //     e.printStackTrace();
        // }

        Response response4 = master.sendHttpReq(spinnObj, URLspinnaker);
        //Failure handling could go here, pass in response and the response type
        if (aFailureChecker.CheckCode(response4)) {
            spinnResp = mapper.readValue(response4.body().byteStream(), SpinnakerServiceResp.class);
            progress.setSpinnaker("finished");
        } else {
            progress.setSpinnaker("failed");
            progress.setRunningStatus(false);
        }

    
        System.out.println("\nrunning spinn: finished");
        progress.setSpinnaker("finished" + spinnResp.getApplicationCreated());
        progress.setSpinnaker("finished" + spinnResp.getPipelineCreated());

        //we have finshed
        progress.setRunningStatus(false);

        return new ProgressResp("finished running", true);
    } 

}
