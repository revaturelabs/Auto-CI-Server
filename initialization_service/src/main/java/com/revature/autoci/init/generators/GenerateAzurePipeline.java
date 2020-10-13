package com.revature.autoci.init.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.revature.autoci.init.generators.utils.Templater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateAzurePipeline {
    static final Logger log = LoggerFactory.getLogger(GenerateAzurePipeline.class);
    
    /**
     * Generate an Azure pipeline from a template.
     * @param isMaven Determines whether to generate a Maven or Node Jenkinsfile
     * @param workingDir the directory to generate the Jenkinsfile in
     * @throws IOException
     */
    public static void generatePipeline(boolean isMaven, String workingDir) throws IOException {
        log.info("Generating Azure Pipeline files");
        generateBranchPipeline(isMaven, "dev", workingDir);
        generateBranchPipeline(isMaven, "prod", workingDir);
        log.info("Azure Pipeline files successfully generated");
    }

    private static void generateBranchPipeline(boolean isMaven, String branch, String workingDir) throws IOException
    {
        // Compile regex to replace variables with arguments
        Map<String, String> pairs = new HashMap<>();
        pairs.put("gitBranch", branch);
        Templater devTemplater = new Templater(pairs);

        // Determine the template to use
        String template = isMaven? "Azure_templates/maven.yaml" : "Azure_templates/npm.yaml";

        // Fill in the template and create new Spinnaker Pipeline
        InputStream templateStream = GenerateAzurePipeline.class.getClassLoader().getResourceAsStream(template);
        try(BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream)))       
        {
            File spinnaker = Paths.get(workingDir, "azure-pipelines-"+branch+".json").toFile();
            spinnaker.createNewFile();
            try(FileWriter writer = new FileWriter(spinnaker))
            {
                devTemplater.fillTemplate(buf, writer, "");
            }
        }
    }

}
