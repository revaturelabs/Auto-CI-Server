package com.revature.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendObj;
import com.revature.model.Jenkins.JenkinsServiceObject;
import com.revature.model.Jenkins.JenkinsServiceResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.Response;

public class Jenkins {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JenkinsServiceResp jenkinsService(FrontendObj frontEndObj, String url, String URLjenkinshost, ConfigurationResp configResp) {

        ProgressSingleton progress = ProgressSingleton.instance();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        progress.setJenkins("started");
        JenkinsServiceObject jenkinsServiceObj = new JenkinsServiceObject();
        JenkinsServiceResp jenkinsResp;
        jenkinsServiceObj.setGithubURL(configResp.getGithubURL());
        jenkinsServiceObj.setJenkinsURL(URLjenkinshost);
        jenkinsServiceObj.setProjectName(frontEndObj.getMavenData().getProjectName());
        jenkinsServiceObj.setSlackChannel(frontEndObj.getSlackChannel());

        Response response = HttpRequest.sendHttpReq(jenkinsServiceObj, url);

        // if(response == null){
        //     progress.setJenkins("failed");
        //     progress.setRunningStatus(false);
        //     jenkinsResp = new JenkinsServiceResp();
        // }

        //checks for 200 response
        if (FailureChecker.CheckCode(response)) {
            try {
                jenkinsResp = mapper.readValue(response.body().byteStream(), JenkinsServiceResp.class);
                log.info("OK");
                progress.setJenkins("finished");
                return jenkinsResp;
            } catch (IOException e) {
                e.printStackTrace();
                progress.setJenkins("failed");
                progress.setRunningStatus(false);
                log.error("Failed setting response = " + e.getMessage());
            }
        } else {
            progress.setJenkins("failed");
            progress.setRunningStatus(false);
            jenkinsResp = new JenkinsServiceResp();
        }
        return null;
    }
    
}
