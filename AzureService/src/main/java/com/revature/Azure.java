package com.revature;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Azure extends HttpServlet {
    String projName;
    String gitUrl;
  
    final String AZ_AUTH = " -u " + System.getenv("AZURE_USERNAME") + " -p " + System.getenv("AZURE_PASSWORD");
    final String AZ_ORG = System.getenv("AZURE_ORG_NAME");
    final String AZ_SERVICE_ID = System.getenv("AZURE_GITHUB_AUTH_SERVICE_ID");
    final String[] BRANCHES = {"dev", "prod"};
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Azure servlet started.");
        JSONObject responseJson = new JSONObject();
        parseParams(request);

        try {
            CommandExecutor cmd = new CommandExecutor();

            log.info("Logging into Azure...");
			azureLogin(cmd, responseJson);
            log.info("Making pipeline...");
            makePipeline(cmd, responseJson);
            log.info("Setting pipeline variables...");
            setPipelineVars(cmd, responseJson);
        } catch (Exception e) {
            responseJson.put("errorMsg", e.getMessage());
            log.error(e.getMessage());
        }
		
        response.setContentType("application/json");
        response.getWriter().write(responseJson.toString(0));
        log.info("Azure servlet has written HTTP response and finished.");
    }

    private void parseParams(HttpServletRequest req) throws IOException {
        log.info("Reading JSON from HTTP request.");
        JSONObject json = parseRequestToJSON(req);
        parseJsonToVars(json);
    }

    private JSONObject parseRequestToJSON(HttpServletRequest req) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            log.error("Exception in parseRequestToJSON method. Issue reading the HTTP request.  " + e.getMessage());
            throw new IOException("Error reading input!");
        }

        JSONObject json = null;
        try {
            json = new JSONObject(jb.toString());
        } catch (JSONException e) {
            log.error("Request string is not JSON: " + jb.toString() + " " + e.getMessage());
            String err = "Request string is not JSON: " + jb.toString();
            throw new IOException(err);
        }
        return json;
    }

    private void parseJsonToVars(JSONObject json) throws IOException {
        try {
            projName = json.getString("projectName");
            gitUrl = json.getString("githubURL");
        } catch (JSONException e) {
            log.error("Exception when trying to parse JSON to Java.  " + e.getMessage());
            String err = "Error parsing JSON request string";
            if (json != null) {
                err += ": " + json.toString();
            }
            log.error("JSON exception occurring with this JSON: " + err);
            throw new IOException(err);
        }
    }

    private void execAndLogCmd(CommandExecutor cmd, String command) {
        String execOutput = cmd.execute(command);
        if (cmd.wasLastCmdSuccess()) {
            log.info(execOutput);
        } else {
            log.error(execOutput);
        }
        System.out.println(execOutput);
    }
	
    private void azureLogin(CommandExecutor cmd, JSONObject response) throws IOException {
        execAndLogCmd(cmd, "az login" + AZ_AUTH);
        execAndLogCmd(cmd, "az devops configure -d organization=https://dev.azure.com/RevatureProjectFactory project=ProjectFactory");
        response.put("login", "success");
    }
    
    private String makePipelineCommand(String branch) {
        return "az pipelines create --folder " + projName + " --name " + projName + "-" + branch + " --repository " + gitUrl + " --branch " + branch + " --yaml-path azure-pipelines.yaml --service-connection " + AZ_SERVICE_ID;
    }
    private void makePipeline(CommandExecutor cmd, JSONObject response) throws IOException {
        execAndLogCmd(cmd, "az pipelines folder create --path " + projName);
        for (String b : BRANCHES) {
            execAndLogCmd(cmd, makePipelineCommand(b));
        }
        response.put("pipeline", "success");
    }
    
    private String makePipelineVarCommand(String name, String value, String branch) {
        return "az pipelines variable create --name " + name + " --value " + value + " --pipeline-name " + projName + "-" + branch;
    }
    private void setPipelineVars(CommandExecutor cmd, JSONObject response) throws IOException {
        for (String b : BRANCHES) {
            execAndLogCmd(cmd, makePipelineVarCommand("projNameLowercase", projName.toLowerCase(), b));
            execAndLogCmd(cmd, makePipelineVarCommand("azureSubscriptionEndpoint", "helm", b));
            execAndLogCmd(cmd, makePipelineVarCommand("keyVaultName", "Project3KeyVault", b));
            execAndLogCmd(cmd, makePipelineVarCommand("azureContainerRegistry", "revprojectfactory.azurecr.io", b));
            execAndLogCmd(cmd, makePipelineVarCommand("azureResourceGroup", "Project3", b));
            execAndLogCmd(cmd, makePipelineVarCommand("kubernetesCluster", "Project3Cluster", b));
            execAndLogCmd(cmd, makePipelineVarCommand("chartPath", "chart/", b));
            execAndLogCmd(cmd, makePipelineVarCommand("azureCRConnection", "docker-acr", b));
        }
        response.put("pipeline-vars", "success");
    }
}
