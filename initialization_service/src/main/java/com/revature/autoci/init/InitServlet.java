package com.revature.autoci.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

public class InitServlet extends HttpServlet {
    private String token;
    private String containerRegistryURL;
    private String containerRegistryCredentialId;

    @Override
    public void init() throws ServletException {
        super.init();
        // Load in github credentials
        token = System.getProperty("GITHUB_TOKEN");
        containerRegistryURL = System.getProperty("CONTAINER_REGISTRY_URL", "REPLACEME");
        containerRegistryCredentialId = System.getProperty("CONTAINER_REGISTRY_URL", "REPLACEME");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load in JSON message
        Gson gson = new Gson();
        JSONRequest data = gson.fromJson(req.getReader(), JSONRequest.class);
        boolean success = false;
        Path tempPath = Files.createTempDirectory("test");
        System.out.println(tempPath.toAbsolutePath().toString());
        try (LocalGitRepo git = new LocalGitRepo(data.getGithubURL(), tempPath, token)) {
            // create temp directory
            String projectName = null;
            if (data.isMaven()) {
                MavenJSON mavenData = data.getMavenData();
                projectName = mavenData.getProjectName();
                System.out.println("Generating maven project");
                
                GenerateMavenProject.generateNewMavenProject(mavenData.getGroupId(),
                    mavenData.getArtifactId(), mavenData.getVersion(), mavenData.getDescription(),
                    mavenData.getDescription(), data.getGithubURL(), mavenData.getPackaging(),
                    mavenData.getJavaVersion(), mavenData.getMainClass(),
                    mavenData.getDependencies(), data.getIDE(), tempPath.toString());
            } else // Is node
            {
                NpmJSON npmData = data.getNpmData();
                projectName = npmData.getProjectName();
                System.out.println("Generating Node project");

                GenerateNpmProject.generateNewNpmProject(npmData.getProjectName(), npmData.getAuthor(), npmData.getVersion(), 
                    npmData.getDescription(), npmData.getMainEntrypoint(), data.getGithubURL(),
                    npmData.getLicense(), npmData.getScripts(), npmData.getKeywords(),
                    npmData.getDependencies(), npmData.getDevDependencies(), data.getIDE(), tempPath.toString());
            }

            // Generate jenkinsfile in top-level directory
            GenerateJenkinsfile.generateJenkinsfile(data.getGithubURL(), containerRegistryURL,
                    "REPLACE_WITH_REGISTRY_USERNAME", projectName,
                    containerRegistryCredentialId, data.isMaven(), tempPath.toString());

            try 
            {
                if(data.shouldGenerateGHActions())
                {
                    GenerateGithubActions.GenerateFile(tempPath.toString());
                }
                
                // git branch, add, commit, push
                git.branchDevAndProd();
                git.addAndCommitAll();
                git.pushToRemote();
                success = true;
            } 
            catch (TimeoutException e) 
            {
                success = false;
                e.printStackTrace();
            }
        }
        catch(GenerationException e)
        {
            success = false;
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
            return;
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
    }
}
