package com.revature.autoci.init.generators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GenerateDockerfile {
    public static void generateDockerfile(String workingDir) throws IOException
    {
        InputStream dockerfile = GenerateDockerfile.class.getClassLoader().getResourceAsStream("Dockerfile");
        Files.copy(dockerfile, Paths.get(workingDir, "Dockerfile"));
    }
}
