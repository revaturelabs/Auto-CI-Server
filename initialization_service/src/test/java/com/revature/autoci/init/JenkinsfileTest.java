package com.revature.autoci.init;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        tempPath.toFile().deleteOnExit();
    }

    @Test
    public void createsFile() throws IOException
    {
        GenerateJenkinsfile.generateJenkinsfile("http://test", "https://testreg", "testUser", "testProj", "abcdef", true, tempPath.toString());
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Jenkinsfile")));

        tempPath = Files.createTempDirectory("jkt");
        tempPath.toFile().deleteOnExit();
        GenerateJenkinsfile.generateJenkinsfile("http://test", "https://testreg", "testUser", "testProj", "abcdef", false, tempPath.toString());
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Jenkinsfile")));
    }

    @Test
    public void CorrectFileTemplate() throws IOException
    {
        GenerateJenkinsfile.generateJenkinsfile("http://test", "https://testreg", "testUser", "testProj", "abcdef", true, tempPath.toString());
        File f = new File(tempPath.toString(), "Jenkinsfile");
        boolean isMaven = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(f)))
        {
            while(reader.ready())
            {
                if(reader.readLine().contains("maven"));
                {
                    isMaven = true;
                }
            }
        }
        assertTrue(isMaven);

        tempPath = Files.createTempDirectory("jkt");
        tempPath.toFile().deleteOnExit();
        GenerateJenkinsfile.generateJenkinsfile("http://test", "https://testreg", "testUser", "testProj", "abcdef", false, tempPath.toString());
        
        f = new File(tempPath.toString(), "Jenkinsfile");
        boolean isNode = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(f)))
        {
            while(reader.ready())
            {
                if(reader.readLine().contains("maven"));
                {
                    isNode = true;
                }
            }
        }
        assertTrue(isNode);
    }
}
