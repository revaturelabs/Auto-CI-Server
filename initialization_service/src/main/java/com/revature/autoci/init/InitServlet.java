package com.revature.autoci.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class InitServlet extends HttpServlet 
{
    private String token;
    @Override
    public void init() throws ServletException {
        super.init();
        // Load in github credentials
        token = System.getProperty("GITHUB_TOKEN", null);
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
                // Call Maven generator
            }
            else // Is node
            {
                // Call Node generator
            }

            // git init, commit, push
            git.addAndCommitAll();
            git.pushToRemote();
            success = true;
        }
        finally
        {
            // delete temp directory
            FileUtils.deleteDirectory(tempPath.toFile());
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
