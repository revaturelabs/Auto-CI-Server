package com.revature.autoci.init;

import java.nio.file.Path;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalGitRepo implements AutoCloseable{
    private String uri;
    private Path cloneDir;
    private CredentialsProvider credentials;
    private Git repo;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public LocalGitRepo(String URI, Path cloneDir, String token) throws GenerationException
    {
        uri = URI;
        this.cloneDir = cloneDir;
        credentials = new UsernamePasswordCredentialsProvider(token, "");
        repo = cloneRepo();
    }

    private Git cloneRepo() throws GenerationException
    {
        CloneCommand cloneCmd = Git.cloneRepository();
        cloneCmd.setURI(uri);
        cloneCmd.setCredentialsProvider(credentials);
        cloneCmd.setDirectory(cloneDir.toFile());
        Git repo = null;
        try
        {
            repo = cloneCmd.call();
            log.info("Cloning local gir repo succeed");
        }
        catch(GitAPIException e)
        {
            System.out.println(e.getMessage());
            log.error("Cloing local git repo failed ", e);
            throw new GenerationException(e.getMessage());
        }
        return repo;
    }

    public void addAndCommitAll()
    {
        AddCommand addCmd = repo.add();
        addCmd.addFilepattern(".");
        try {
            addCmd.call();
            log.info("Adding and commiting repo succeed");
        } catch (GitAPIException e) {
            log.error("Adding and commiting repo failed ", e);
            e.printStackTrace();
        }
        CommitCommand commitCmd = repo.commit();
        commitCmd.setAuthor("Auto-CI", "<>");
        commitCmd.setCommitter("Auto-CI", "<>");
        commitCmd.setMessage("Setting up a new project");
        try {
            commitCmd.call();
            log.info("commiting author, committer, message succeed");
        } catch (GitAPIException e) {
            log.error("Calling commit command failed ", e);;
            e.printStackTrace();
        }
    }

    public void pushToRemote()
    {
        PushCommand pushCmd = repo.push();
        pushCmd.setRemote(uri);
        pushCmd.setCredentialsProvider(credentials);
        try {
            pushCmd.call();
            log.info("Push repo to repote succeed");
        } catch (GitAPIException e) {
            log.error("pushing to repore failed", e);
            e.printStackTrace();
        }
    }

    public void close()
    {
        repo.getRepository().close();
        repo.close();
    }
}
