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
            throw new GenerationException("Failed to clone repository.");
        }
        return repo;
    }

    public void branchDevAndProd() throws GenerationException
    {
        try {
            repo.branchCreate().setName("dev").call();
            repo.branchCreate().setName("prod").call();
        } catch (GitAPIException e) {
            throw new GenerationException("Failed to create dev and prod branches.");
        }
        
    }

    public void addAndCommitAll() throws GenerationException
    {
        AddCommand addCmd = repo.add();
        addCmd.addFilepattern(".");
        try {
            addCmd.call();
        } catch (GitAPIException e) {
            throw new GenerationException("Failed to stage files.");
        }
        CommitCommand commitCmd = repo.commit();
        commitCmd.setAuthor("Project Factory", "<>");
        commitCmd.setCommitter("Project Factory", "<>");
        commitCmd.setMessage("New project, hot off the press");
        try {
            commitCmd.call();
        } catch (GitAPIException e) {
            throw new GenerationException("Failed to commit staged files");
        }
    }

    public void pushToRemote() throws GenerationException
    {
        PushCommand pushCmd = repo.push();
        pushCmd.setRemote(uri);
        pushCmd.setCredentialsProvider(credentials);
        pushCmd.setPushAll();
        try {
            pushCmd.call();
        } catch (GitAPIException e) {
            throw new GenerationException("Failed to push to remote");
        }
    }

    public void close()
    {
        repo.getRepository().close();
        repo.close();
    }
}
