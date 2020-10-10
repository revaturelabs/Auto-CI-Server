package com.revature.controller;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendReq;
import com.revature.model.Initialization.InitializationObj;
import com.revature.model.Initialization.InitializationResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.Response;

public class Initialization {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public InitializationResp initService(FrontendReq frontendObj, String url, ConfigurationResp configResp) {

        ProgressSingleton progress = ProgressSingleton.instance();
        ObjectMapper mapper = new ObjectMapper();

        progress.setInitialization("started");
        InitializationObj initObj = new InitializationObj();
        initObj.setGithubURL(configResp.getGithubURL());
        initObj.setGenerateGithubActions(frontendObj.getGenerateGithubActions());
        initObj.setGithubUsername(frontendObj.getGithubUsername());
        initObj.setIde(frontendObj.getIde());
        initObj.setIsMaven(frontendObj.getIsMaven());
        initObj.setMavenData(frontendObj.getMavenData());
        initObj.setNpmData(frontendObj.getNpmData());
        InitializationResp initResp;
        Response response = HttpRequest.sendHttpReq(initObj, url);

        //checks for 200 response
        if (FailureChecker.CheckCode(response)) {
            try {
                initResp = mapper.readValue(response.body().byteStream(), InitializationResp.class);
                log.info("OK");
                progress.setInitialization("finished");
                return initResp;
            } catch (IOException e) {
                e.printStackTrace();
                progress.setInitialization("failed");
                progress.setRunningStatus(false);
                log.error("Failed setting response = " + e.getMessage());
            }
        } else {
            progress.setInitialization("failed");
            progress.setRunningStatus(false);
            initResp = new InitializationResp();
        }
        return null;
    }
}