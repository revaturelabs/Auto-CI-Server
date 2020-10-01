package com.revature.autoci.init.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.revature.autoci.init.generators.utils.Templater;

public class GenerateJenkinsfile {
    private static final Logger log = LoggerFactory.getLogger(GenerateJenkinsfile.class);

    // Generates a Jenkinsfile from a template. The template used depends on whether
    // it is a Maven or NPM project.
    public static void generateJenkinsfile(String githubURL, String dockerRegistryURL, String dockerUser,
            String projectName, String credentialId, boolean isMaven, String pathToProject) throws IOException {
        // Compile regex to replace variables with arguments
        Map<String, String> pairs = new HashMap<>();
        pairs.put("githubURL", githubURL);
        pairs.put("dockerUser", dockerUser);
        pairs.put("projectName", projectName);
        pairs.put("ContainerRegistryURL", dockerRegistryURL);
        pairs.put("ContainerRegistryCredId", credentialId);
        Templater templater = new Templater(pairs);

        // Determine the template to use
        String template = isMaven ? "Jenkinsfile_templates/Jenkinsfile_maven" : "Jenkinsfile_templates/Jenkinsfile_npm";

        // Fill in the template and create new Jenkinsfile
        InputStream templateStream = GenerateJenkinsfile.class.getClassLoader().getResourceAsStream(template);
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream))) {
            File jenkinsfile = Paths.get(pathToProject, "Jenkinsfile").toFile();
            jenkinsfile.createNewFile();
            try (FileWriter writer = new FileWriter(jenkinsfile)) {
                templater.fillTemplate(buf, writer, "\"");
                log.info("Template and Jenkinsfile Created");
            }
        }
    }
}
