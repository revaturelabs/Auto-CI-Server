package com.revature.controller;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.ConfigurationObj;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.Response;

public class Configuration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ConfigurationResp configService(FrontendObj frontEndObj, String UrlConfig, String Urljenkins) {

        ProgressSingleton progress = ProgressSingleton.instance();
        ObjectMapper mapper = new ObjectMapper();

        progress.setConfiguration("started");
        ConfigurationObj configObj = new ConfigurationObj();
        ConfigurationResp configResp;
        configObj.setGithubUsername(frontEndObj.getGithubUsername());
        configObj.setJenkinsURL(Urljenkins);
        configObj.setProjectName(frontEndObj.getMavenData().getProjectName());
        configObj.setMakeJenkinsWebhook(frontEndObj.getMakeJenkinsWebhook());
        configObj.setDebug(true);

        Response response = HttpRequest.sendHttpReq(configObj, UrlConfig);

        // if(response == null){
        //     progress.setConfiguration("failed");
        //     progress.setRunningStatus(false);
        //     configResp = new ConfigurationResp();
        //     return configResp;
        // }

        //checks for 200 response
        if (FailureChecker.CheckCode(response)) {
            try {
                configResp = mapper.readValue(response.body().byteStream(), ConfigurationResp.class);
                log.info("OK");
                progress.setConfiguration("finished");
                return configResp;
            } catch (IOException e) {
                e.printStackTrace();
                progress.setConfiguration("failed");
                progress.setRunningStatus(false);
                log.error("Failed setting response = " + e.getMessage());
            }
        } else {
            progress.setConfiguration("failed");
            progress.setRunningStatus(false);
            configResp = new ConfigurationResp();
        }
        return null;
    }
    
}
