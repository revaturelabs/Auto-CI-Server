package com.revature.autoci.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SystemUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateGithubActions {
    static final String GITHUB_ACTIONS_SCRIPT = "GithubActionsScript";
    private static final Logger log = LoggerFactory.getLogger(GenerateGithubActions.class);

    // Generate the Github Actions yaml and directories. Due to the nature of the implementation as a shell script, the
    // script must be copied to the working directory, executed, then deleted.
    public static void GenerateFile(String pathToDir) throws IOException, TimeoutException
    {
        // Copy script to directory
        Path scriptPath = Paths.get(pathToDir, GITHUB_ACTIONS_SCRIPT);
        Files.copy(GenerateGithubActions.class.getClassLoader().getResourceAsStream(GITHUB_ACTIONS_SCRIPT), scriptPath);
        ProcessBuilder runner = new ProcessBuilder();
        runner.directory(Paths.get(pathToDir).toFile());
        // This command sucks, really just don't run it on windows except for testing
        if(SystemUtils.IS_OS_WINDOWS)
        {
            runner.command("cmd.exe", "/C", "C:/Program Files/Git/bin/bash.exe", "GithubActionsScript");
        }
        else
        {
            runner.command("sh", "GithubActionsScript");
        }

        // Run script
        Process proc = runner.start();
        boolean finished = false;
        try
        {
            finished = proc.waitFor(3, TimeUnit.SECONDS);
            log.info("Github Action Generate Command successfully ran");
        }
        catch(InterruptedException e)
        {
            finished = false;
        }
        if(!finished)
        {
            proc.destroyForcibly();
            log.warn("Github action connection failed, timed out or interrupted");
            throw new TimeoutException("Process timed out or interrupted");
        }

        // Remove script
        scriptPath.toFile().delete();
    }
}
