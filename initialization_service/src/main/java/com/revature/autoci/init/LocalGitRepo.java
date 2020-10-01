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

public class LocalGitRepo implements AutoCloseable{
    private String uri;
    private Path cloneDir;
    private CredentialsProvider credentials;
    private Git repo;
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
        }
        catch(GitAPIException e)
        {
            System.out.println(e.getMessage());
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
        } catch (GitAPIException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CommitCommand commitCmd = repo.commit();
        commitCmd.setAuthor("Auto-CI", "<>");
        commitCmd.setCommitter("Auto-CI", "<>");
        commitCmd.setMessage("Setting up a new project");
        try {
            commitCmd.call();
        } catch (GitAPIException e) {
            // TODO Auto-generated catch block
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
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void close()
    {
        repo.getRepository().close();
        repo.close();
    }
}
