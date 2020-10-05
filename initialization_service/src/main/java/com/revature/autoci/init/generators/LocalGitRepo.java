package com.revature.autoci.init.generators;

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

/**
 * Object representing a locally cloned Git repository. Provides methods to
 * manipulate the local repository and push changes to the remote repository.
 */
public class LocalGitRepo implements AutoCloseable {
    private String uri;
    private Path cloneDir;
    private CredentialsProvider credentials;
    private Git repo;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public LocalGitRepo(String URI, Path cloneDir, String token) throws GenerationException {
        uri = URI;
        this.cloneDir = cloneDir;
        credentials = new UsernamePasswordCredentialsProvider(token, "");
        repo = cloneRepo();
    }

    /**
     * Returns the underlying Git object
     * 
     * @return the Git object used by this object
     */
    public Git getGitObject() {
        return repo;
    }

    private Git cloneRepo() throws GenerationException {
        CloneCommand cloneCmd = Git.cloneRepository();
        cloneCmd.setURI(uri);
        cloneCmd.setCredentialsProvider(credentials);
        cloneCmd.setDirectory(cloneDir.toFile());
        Git repo = null;
        try {
            repo = cloneCmd.call();
            log.info("Cloning local gir repo succeed");
        } catch (GitAPIException e) {
            System.out.println(e.getMessage());
            log.error("Cloing local git repo failed ", e);
            throw new GenerationException(e.getMessage());
        }
        return repo;
    }

    /**
     * Creates a dev and prod branch based on the current branch.
     * 
     * @throws GenerationException
     */
    public void branchDevAndProd() throws GenerationException {
        try {
            repo.branchCreate().setName("dev").call();
            repo.branchCreate().setName("prod").call();
        } catch (GitAPIException e) {
            throw new GenerationException("Failed to create dev and prod branches.");
        }

    }

    /**
     * Adds all files in the working branch, and commits them.
     * 
     * @throws GenerationException
     */
    public void addAndCommitAll() throws GenerationException {
        AddCommand addCmd = repo.add();
        addCmd.addFilepattern(".");
        try {
            addCmd.call();
            log.info("Adding and commiting repo succeed");
        } catch (GitAPIException e) {
            log.error("Adding and commiting repo failed ", e);
            throw new GenerationException("Failed to stage files.");
        }
        CommitCommand commitCmd = repo.commit();
        commitCmd.setAuthor("Project Factory", "<>");
        commitCmd.setCommitter("Project Factory", "<>");
        commitCmd.setMessage("New project, hot off the press");
        try {
            commitCmd.call();
            log.info("commiting author, committer, message succeed");
        } catch (GitAPIException e) {
            log.error("Calling commit command failed ", e);
            throw new GenerationException("Failed to commit staged files");
        }
    }

    /**
     * Pushes the currently committed changes to the remote repository.
     * 
     * @throws GenerationException
     */
    public void pushToRemote() throws GenerationException {
        PushCommand pushCmd = repo.push();
        pushCmd.setRemote(uri);
        pushCmd.setCredentialsProvider(credentials);
        pushCmd.setPushAll();
        try {
            pushCmd.call();
            log.info("Push repo to remote succeed");
        } catch (GitAPIException e) {
            log.error("pushing to remote failed", e);
            throw new GenerationException("Failed to push to remote");
        }
    }

    /**
     * Closes the Git object and underlying repository.
     */
    public void close() {
        repo.getRepository().close();
        repo.close();
    }
}
