package com.revature.autoci.init.generators;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class DockerTest {
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
        GenerateDockerfile.generateDockerfile(tempPath.toString());
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Dockerfile")));

        tempPath = Files.createTempDirectory("jkt");
        tempPath.toFile().deleteOnExit();
        GenerateDockerfile.generateDockerfile(tempPath.toString());
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Dockerfile")));
    }
}
