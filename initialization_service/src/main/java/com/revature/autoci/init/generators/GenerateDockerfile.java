package com.revature.autoci.init.generators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class provides static methods for generating a Dockerfile
 */
public class GenerateDockerfile {

    /**
     * Generates a simple Dockerfile
     * @param workingDir The directory to generate the Dockerfile in.
     * @throws IOException
     */
    public static void generateDockerfile(String workingDir) throws IOException
    {
        InputStream dockerfile = GenerateDockerfile.class.getClassLoader().getResourceAsStream("Dockerfile");
        Files.copy(dockerfile, Paths.get(workingDir, "Dockerfile"));
    }
}
