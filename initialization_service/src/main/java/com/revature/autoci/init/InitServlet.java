package com.revature.autoci.init;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import com.revature.autoci.init.generators.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitServlet extends HttpServlet {
    static final String SECRET_DIR = "secrets/";
    private String token;
    private String containerRegistryURL;
    private String containerRegistryCredentialId;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        // Load in github credentials
        if((token = System.getProperty("GITHUB_TOKEN")) == null)
        {
            try
            {
                
                token = new String(Files.readAllBytes(Paths.get(SECRET_DIR, "github-token")), StandardCharsets.UTF_8).trim();
                log.info("Github credentials succesfully received");
            }
            catch(IOException e)
            {
                log.error("Failed to find github token in environment or secrets", e);;
                throw new ServletException("Failed to find github token in environment or secrets/");
            }
        }
        containerRegistryURL = System.getProperty("CONTAINER_REGISTRY_URL", "REPLACEME");
        containerRegistryCredentialId = System.getProperty("CONTAINER_CREDENTIAL_ID", "REPLACEME");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load in JSON message
        Gson gson = new Gson();
        JSONRequest data = gson.fromJson(req.getReader(), JSONRequest.class);
        boolean success = false;
        Path tempPath = Files.createTempDirectory("init");
        System.out.println(tempPath.toAbsolutePath().toString());
        try (LocalGitRepo git = new LocalGitRepo(data.getGithubURL(), tempPath, token)) {
            // create temp directory
            String projectName = null;
            String appVersion = null;
            String imageName = null;
            if (data.isMaven()) {
                MavenJSON mavenData = data.getMavenData();
                projectName = mavenData.getProjectName();
                appVersion = mavenData.getVersion();
                System.out.println("Generating maven project");
                
                GenerateMavenProject.generateNewMavenProject(mavenData.getGroupId(),
                    mavenData.getArtifactId(), mavenData.getVersion(), mavenData.getDescription(),
                    mavenData.getDescription(), data.getGithubURL(), mavenData.getPackaging(),
                    mavenData.getJavaVersion(), mavenData.getMainClass(),
                    mavenData.getDependencies(), data.getIDE(), tempPath.toString());
                log.info("Maven Project with all required files and configuration succesfully generated");
            } else // Is node
            {
                NpmJSON npmData = data.getNpmData();
                projectName = npmData.getProjectName();
                appVersion = npmData.getVersion();
                System.out.println("Generating Node project");

                GenerateNpmProject.generateNewNpmProject(npmData.getProjectName(), npmData.getAuthor(), npmData.getVersion(), 
                    npmData.getDescription(), npmData.getMainEntrypoint(), data.getGithubURL(),
                    npmData.getLicense(), npmData.getScripts(), npmData.getKeywords(),
                    npmData.getDependencies(), npmData.getDevDependencies(), data.getIDE(), tempPath.toString());
                log.info("Node JS Project with all required files and configuration succesfully generated");
            }
            imageName = projectName;
            // Generate Helm Chart
            log.info("HELM chart with configuration and files successfully generated");
            HelmGenerate.generateHelmChart(projectName.trim().replace(' ', '-'), appVersion, projectName, tempPath.toString(), false);

            // Generate Spinnaker pipeline JSON file
            GenerateSpinnaker.generateSpinnaker(projectName, tempPath.toString());
            log.info("Pipeline JSON file with configuration successfully generated");

            // Generate jenkinsfile in top-level directory
            GenerateJenkinsfile.generateJenkinsfile(data.getGithubURL(), containerRegistryURL,
                    "REPLACE_WITH_REGISTRY_USERNAME", projectName,
                    containerRegistryCredentialId, data.isMaven(), tempPath.toString());
                log.info("Jenkinsfile successfully generated");

            try 
            {
                if(data.shouldGenerateGHActions())
                {
                    GenerateGithubActions.GenerateFile(tempPath.toString());
                }
                
                // git add, commit, push
                git.addAndCommitAll();
                git.pushToRemote();
                success = true;
                log.info("Github adding, commiting and push actions are successful");
            } 
            catch (TimeoutException e) 
            {
                success = false;
                log.error("Github commands failed due to timeout ", e);
                e.printStackTrace();
            }
        }
        catch(GenerationException e)
        {
            success = false;
            log.error("Github command generation failed", e);
            e.printStackTrace();
        }
        finally
        {
            // delete temp directory
            if(!SystemUtils.IS_OS_WINDOWS)
            {
                FileUtils.deleteDirectory(tempPath.toFile());
            }
        }
        
        if(success)
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            log.info("Servelet functioned as desired");
            return;
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Servelet did not function as desired");
            return;
        }
        
    }
}
