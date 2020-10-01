package com.revature.autoci.init;

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
        Map<Pattern, String> replaceVals = compileRegex(pairs);

        // Determine the template to use
        String template = isMaven ? "Jenkinsfile_templates/Jenkinsfile_maven" : "Jenkinsfile_templates/Jenkinsfile_npm";

        // Fill in the template and create new Jenkinsfile
        InputStream templateStream = GenerateJenkinsfile.class.getClassLoader().getResourceAsStream(template);
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream))) {
            File jenkinsfile = Paths.get(pathToProject, "Jenkinsfile").toFile();
            jenkinsfile.createNewFile();
            try (FileWriter writer = new FileWriter(jenkinsfile)) {
                replaceVariablesInFile(buf, writer, replaceVals);
                log.info("Template and Jenkinsfile Created");
            }
        }
    }

    private static Map<Pattern, String> compileRegex(Map<String, String> pairs) {
        Map<Pattern, String> map = new HashMap<>();
        for (Entry<String, String> pair : pairs.entrySet()) {
            Pattern p = Pattern.compile("\\$\\{\\s*" + pair.getKey() + "\\s*\\}");
            map.put(p, pair.getValue());
        }
        return map;
    }

    private static void replaceVariablesInFile(BufferedReader reader, Writer writer, Map<Pattern, String> map)
            throws IOException {
        while (reader.ready()) {
            String s = reader.readLine();
            for (Entry<Pattern, String> entry : map.entrySet()) {
                Matcher m = entry.getKey().matcher(s);
                if (m.find()) {
                    s = m.replaceAll("\'" + entry.getValue() + "\'");
                }
            }
            writer.write(s + "\n");
        }
        log.info("JenkinsFile information updated");
    }
}
