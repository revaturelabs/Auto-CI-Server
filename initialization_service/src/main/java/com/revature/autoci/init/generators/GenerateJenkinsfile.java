package com.revature.autoci.init.generators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateJenkinsfile {
    private static final Logger log = LoggerFactory.getLogger(GenerateJenkinsfile.class);

    // Generates a Jenkinsfile from a template. The template used depends on whether
    // it is a Maven or NPM project.
    public static void generateJenkinsfile(boolean isMaven, String workingDir) throws IOException {
        // Compile regex to replace variables with arguments

        // Determine the template to use
        String template = isMaven ? "Jenkinsfile_templates/Jenkinsfile_maven" : "Jenkinsfile_templates/Jenkinsfile_npm";

        // Fill in the template and create new Jenkinsfile
        InputStream templateStream = GenerateJenkinsfile.class.getClassLoader().getResourceAsStream(template);
        Files.copy(templateStream, Paths.get(workingDir, "Jenkinsfile"));
        log.info("Finished copying Jenkinsfile");
    }
}
