package com.revature.autoci.init.generators;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides static methods for generating a Jenkinsfile
 */
public class GenerateJenkinsfile {
    private static final Logger log = LoggerFactory.getLogger(GenerateJenkinsfile.class);

    /**
     * Generate a Jenkinsfile from a template.
     * @param isMaven Determines whether to generate a Maven or Node Jenkinsfile
     * @param workingDir the directory to generate the Jenkinsfile in
     * @throws IOException
     */
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
