package com.revature.controller;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Azure.AzureObj;
import com.revature.model.Azure.AzureRespObj;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.Response;

public class Azure {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AzureRespObj azureService(FrontendObj frontendObj, ConfigurationResp configResp, String urlAzure) {

        ProgressSingleton progress = ProgressSingleton.instance();
        ObjectMapper mapper = new ObjectMapper();

        progress.setAzure("started");
        AzureObj azureObj = new AzureObj();
        azureObj.setGithubURL(configResp.getGithubURL());
        azureObj.setProjectName(frontendObj.getMavenData().getProjectName());
        AzureRespObj azureRespObj;

        Response response = HttpRequest.sendHttpReq(azureObj, urlAzure);

        //checks for 200 response
        if (FailureChecker.CheckCode(response)) {
            try {
                azureRespObj = mapper.readValue(response.body().byteStream(), AzureRespObj.class);
                log.info("OK");
                progress.setAzure("finished");
                return azureRespObj;
            } catch (IOException e) {
                e.printStackTrace();
                progress.setAzure("failed");
                progress.setRunningStatus(false);
                log.error("Failed setting response = " + e.getMessage());
            }
        } else {
            progress.setAzure("failed");
            progress.setRunningStatus(false);
            azureRespObj = new AzureRespObj();
        }
        return null;
    }
    
}
