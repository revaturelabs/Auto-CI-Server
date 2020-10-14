package com.revature.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.model.Azure.AzureRespObj;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendObj;
import com.revature.model.Initialization.InitializationResp;
import com.revature.model.Jenkins.JenkinsServiceResp;
import com.revature.model.Progress.ReturnResp;
import com.revature.model.Spinnaker.SpinnakerServiceResp;

public class StartPipeline {

    private final String URLconfig = "http://aa7d4312010bb42048cb10bf1f6ff356-640074528.us-east-1.elb.amazonaws.com:8080/config/";
    private final String URLinit = "http://acefd179b3bf24f75a454ad47bab768e-575975828.us-east-1.elb.amazonaws.com/init/";
    private final String URLjenkins = "http://ace4f487a5b1c4ff986fa0bd92cbb63b-887864636.us-east-1.elb.amazonaws.com:8080/jenkins-svlt/";
    private final String URLjenkinsHost = "http://af8326665ef674d8badfbcfaf654ba6d-1042984266.us-east-1.elb.amazonaws.com:8080/";
    private final String URLspinnaker = "http://a0db1c05a05a74349818c2986d5b37be-2043532376.us-east-1.elb.amazonaws.com:8080/api/pipeline";
    private final String URLAzure = "http://aa9f450e4e7224fca8b41cd504edf8d3-1905482264.us-east-1.elb.amazonaws.com:8080/azure/";

    // private final String URLconfig = "http://localhost:8080/test-config";
    // private final String URLinit = "http://localhost:8080/test-init";
    // private final String URLjenkins = "http://localhost:8080/test-jenkins";
    // private final String URLjenkinsHost = "test.jenkins.com";
    // private final String URLspinnaker = "http://localhost:8080/test-spinn";
    // private final String URLAzure = "http://localhost:8080/test-azure";

    public ReturnResp init(FrontendObj frontEndObj) {

        // to keep track of progress
        ProgressSingleton progress = ProgressSingleton.instance();
        ReturnResp response = new ReturnResp();

        //1
        ConfigurationResp configResp = new Configuration().configService(frontEndObj, URLconfig, URLjenkinsHost);
        printJson(configResp);
                
        // 2
        InitializationResp initResp = new Initialization().initService(frontEndObj, URLinit, configResp);
        printJson(initResp);
        
        //3 - azure pipeline is true run azure and skip jenkins and spinnaker
        if(frontEndObj.getIsAzure()){
            AzureRespObj azure = new Azure().azureService(frontEndObj, configResp, URLAzure);
            printJson(azure);
        } else {
            //3
            JenkinsServiceResp jenkinsResp = new Jenkins().jenkinsService(frontEndObj, URLjenkins, URLjenkinsHost, configResp);
            printJson(jenkinsResp);
    
            // 4
            SpinnakerServiceResp spinnResp = new Spinnaker().spinnService(frontEndObj, URLspinnaker, configResp);
            printJson(spinnResp);
        }

        //we have finshed
        progress.setRunningStatus(false);
        response.setMessage("Completed Pipeline");
        response.setStatus(true);

        return response;
    }

    static <T> void printJson(T obj){
        // pretty print test
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json;
        try {
            json = mapper.writeValueAsString(obj);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
