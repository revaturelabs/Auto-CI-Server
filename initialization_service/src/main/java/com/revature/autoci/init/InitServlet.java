package com.revature.autoci.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

public class InitServlet extends HttpServlet 
{
    private String token;
    @Override
    public void init() throws ServletException {
        super.init();
        // Load in github credentials
        token = System.getProperty("GITHUB_TOKEN", "");
    } 

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load in JSON message
        Gson gson = new Gson();
        JSONRequest data = gson.fromJson(req.getReader(), JSONRequest.class);
        boolean success = false;
        Path tempPath = Files.createTempDirectory("test");
        System.out.println(tempPath.toAbsolutePath().toString());
        try(LocalGitRepo git = new LocalGitRepo(data.getGithubURL(), tempPath, token))
        {
            // create temp directory
            if(data.isMaven())
            {
                System.out.println("Generating maven project");
                GenerateMavenProject.generateNewMavenProject(data.getMetadataValue("groupId"), data.getMetadataValue("artifactId"), 
                "0.0.1", data.getMetadataValue("description"), data.getMetadataValue("name"), data.getGithubURL(), 
                data.getMetadataValue("packaging"), data.getMetadataValue("javaVersion"), data.getMetadataValue("mainClass"), 
                data.getDependencies(), data.getMetadataValue("IDE"), tempPath.toAbsolutePath().toString());
            }
            else // Is node
            {
                // Call Node generator
            }

            // git add, commit, push
            git.addAndCommitAll();
            git.pushToRemote();
            success = true;
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