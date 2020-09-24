package com.revature.autoci.init;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class JenkinsfileTest 
{
    Path tempPath;
    @Before
    public void setup() throws IOException
    {
        tempPath = Files.createTempDirectory("jkt");
    }

    @Test
    public void createsFile() throws IOException
    {
        GenerateJenkinsfile.generateJenkinsfile("http://test", "testUser", "testProj", "abcdef", tempPath.toString());
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Jenkinsfile")));
    }
}
