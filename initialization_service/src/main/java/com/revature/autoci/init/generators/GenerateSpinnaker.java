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

public class GenerateSpinnaker {
    private static final Logger log = LoggerFactory.getLogger(GenerateSpinnaker.class);
    // Generates a Jenkinsfile from a template. The template used depends on whether it is a Maven or NPM project.
    public static void generateSpinnaker(String projectName, String pathToProject) throws IOException
    {
        // Compile regex to replace variables with arguments
        Map<String, String> pairs = new HashMap<>();
        pairs.put("projectName", projectName);
        Templater templater = new Templater(pairs);

        // Determine the template to use
        String template = "spinnaker.json";

        // Fill in the template and create new Spinnaker Pipeline
        InputStream templateStream = GenerateSpinnaker.class.getClassLoader().getResourceAsStream(template);
        try(BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream)))       
        {
            File spinnaker = Paths.get(pathToProject,"Spinnaker.json").toFile();
            spinnaker.createNewFile();
            try(FileWriter writer = new FileWriter(spinnaker))
            {
                templater.fillTemplate(buf, writer, "\'");
                log.info("Spinnaker Pipeline Template successfully updated");
            }
        }
    }
}
