package com.revature.autoci.init.generators;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalGitRepoTest {
    LocalGitRepo repo;
    Git git;
    Path tempDir;

    @Before
    public void setup() throws IOException, GenerationException {
        tempDir = Files.createTempDirectory("lgr");
        repo = new LocalGitRepo("https://github.com/octocat/Hello-World", tempDir, "");
        git = repo.getGitObject();
    }

    @After
    public void teardown() {
        repo.close();
    }

    @Test
    public void cloneTest() {
        assertTrue(Paths.get(tempDir.toString(), ".git").toFile().isDirectory());
    }

    @Test
    public void commitTest() throws Exception {
        Paths.get(tempDir.toString(), "example.txt").toFile().createNewFile();
        repo.addAndCommitAll();
        List<DiffEntry> diffs = diffList();
        boolean success = false;
        for(DiffEntry d: diffs)
        {
            if(d.getOldPath().equals("example.txt"))
            {
                success = true;
            }
        }
    }

    @Test
    public void branchTest() throws GenerationException, GitAPIException
    {
        repo.branchDevAndProd();
        String[] targetBranches = {"dev", "prod"};
        List<Ref> branches = git.branchList().call();
        List<String> names = branches.stream().map(b -> b.getName().split("/")[2]).collect(Collectors.toList());
        assertTrue(names.containsAll(Arrays.asList(targetBranches)));
    }

    // From https://stackoverflow.com/a/28793479
    public List<DiffEntry> diffList()
            throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException
    {
        ObjectReader reader = git.getRepository().newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        ObjectId oldTree = git.getRepository().resolve( "HEAD~1^{tree}" );
        oldTreeIter.reset( reader, oldTree );
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        ObjectId newTree = git.getRepository().resolve( "HEAD^{tree}" );
        newTreeIter.reset( reader, newTree );

        DiffFormatter diffFormatter = new DiffFormatter( DisabledOutputStream.INSTANCE );
        diffFormatter.setRepository( git.getRepository() );
        List<DiffEntry> entries = diffFormatter.scan( oldTreeIter, newTreeIter );

        return entries;

    }
}