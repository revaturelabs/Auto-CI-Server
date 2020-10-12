package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendObj;
import com.revature.model.Initialization.InitializationObj;
import com.revature.model.Initialization.InitializationResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.Response;

public class Initialization {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public InitializationResp initService(FrontendObj frontendObj, String url, ConfigurationResp configResp) {

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
        InitializationResp initResp = new InitializationResp();
        Response response = HttpRequest.sendHttpReq(initObj, url);

        //checks for 200 response
        if (FailureChecker.CheckCode(response)) {
            log.info("OK");
            initResp.setIsDone("done");
            progress.setInitialization("finished");
            return initResp;
        } else {
            progress.setInitialization("failed");
            progress.setRunningStatus(false);
            initResp.setIsDone("failed");
            return initResp;
        }
    }
}