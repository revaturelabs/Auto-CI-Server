package com.revature.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.ConfigurationResp;
import com.revature.model.Frontend.FrontendObj;
import com.revature.model.Spinnaker.SpinnakerServiceObject;
import com.revature.model.Spinnaker.SpinnakerServiceResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.Response;

public class Spinnaker {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SpinnakerServiceResp spinnService(FrontendObj frontendObj, String url, ConfigurationResp configResp) {

        ProgressSingleton progress = ProgressSingleton.instance();
        ObjectMapper mapper = new ObjectMapper();

        progress.setSpinnaker("started");
        SpinnakerServiceObject spinnObj = new SpinnakerServiceObject();
        SpinnakerServiceResp spinnResp;
        spinnObj.setGitUri(configResp.getGithubURL());
        List<String> listProviders = new ArrayList<>();
        listProviders.add("kubernetes");
        listProviders.add("AWS");
        spinnObj.setCloudProviders(listProviders);
        spinnObj.setEmail("testemail");
        spinnObj.setProjectName(frontendObj.getMavenData().getProjectName());
        spinnObj.setBranch("main");

        Response response = HttpRequest.sendHttpReq(spinnObj, url);

        // if(response == null){
        //     progress.setSpinnaker("failed");
        //     progress.setRunningStatus(false);
        //     spinnResp = new SpinnakerServiceResp();
        // }

        //checks for 200 response
        if (FailureChecker.CheckCode(response)) {
            try {
                spinnResp = mapper.readValue(response.body().byteStream(), SpinnakerServiceResp.class);
                log.info("OK");
                progress.setSpinnaker("finished");
                return spinnResp;
            } catch (IOException e) {
                e.printStackTrace();
                progress.setSpinnaker("failed");
                progress.setRunningStatus(false);
                log.error("Failed setting response = " + e.getMessage());
            }
        } else {
            progress.setSpinnaker("failed");
            progress.setRunningStatus(false);
            spinnResp = new SpinnakerServiceResp();
        }
        return null;
    }
}