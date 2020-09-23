package com.revature.autoci.init;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;


public class HelmGenerate {

    public static void chartGenerate(String chartName, String apiVersion, String type, String directoryToPush) {

        List<String> lines = new ArrayList<String>();
        String line;

        ProcessBuilder processBuilder = new ProcessBuilder();

        // check system
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        // writting command
        String buildcommand = "helm create " + chartName;

        processBuilder.directory(new File(directoryToPush));

        // setting command
        if (isWindows) {

            processBuilder.command("cmd.exe", "/c", buildcommand);

        } else {

            processBuilder.command("sh", "-c", buildcommand);

        }

        // using command
        try {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nBuilt with " + exitCode + " error(s).");

            String path  = Paths.get(directoryToPush, chartName + "/Chart.yaml").toString();
            File newchart = new File(path);
            FileReader fr = new FileReader(newchart);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.contains("description: A Helm chart for Kubernetes"))
                    line = line.replace("description: A Helm chart for Kubernetes",
                            "description: This is a test chart for sample " + chartName);
                lines.add(line);
            }
            while ((line = br.readLine()) != null) {
                if (line.contains("apiVersion: v2"))
                    line = line.replace("apiVersion: v2", "apiVersion: " + apiVersion);
                lines.add(line);
            }
            while ((line = br.readLine()) != null) {
                if (line.contains("type: application"))
                    line = line.replace("type: application", "type: " + type);
                lines.add(line);
            }
            fr.close();
            br.close();

            FileWriter fw = new FileWriter(newchart);
            BufferedWriter out = new BufferedWriter(fw);
            String newLine = System.getProperty("line.separator");
            for (String s : lines) {
                out.write(s + newLine);
                out.flush();
            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // this method is solely for creating a template
 
    public static void helmTemplates(String chartName, String apiVersion, String kind, String templateName,
            String data, String directoryToPush) {
        String filepath = Paths.get(directoryToPush, chartName + "/templates/", templateName+".yaml" ).toString();

        Writer writer;
        try {
            writer = new FileWriter(filepath);
            String newLine = System.getProperty("line.separator");
            writer.write("apiVersion: " + apiVersion + newLine);
            writer.write("kind: " + kind + newLine);
            writer.write("metadata:" + newLine);
            writer.write("  name: {{ .Release.Name }}-" + templateName + newLine);
            writer.write("data:" + newLine);
            writer.write("  myvalue: " + data + newLine);

            writer.close();

            System.out.println("new template created");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // To Run:

// public static void main(String[] args) {
//     HelmGenerate test = new HelmGenerate();
//     test.chartGenerate("testChart", "v2", "application","C:/Users/xxx/");
//     test.helmTemplates("testChart", "v1", "ConfigMap", "configmap-test", "\"hello there\"", "C:/Users/xxx/");
//     }
    

}



// Quick Explaination: 
// creating new instance of Helm Generate: HelmGenerate test = new
// HelmGenerate();
// Generating a new Helm Chart Chart: test.ChartGenerate("testChart", "v2",
// "application");
// Create a template
// test.HelmTemplates("testChart", "v1", "ConfigMap", "configmap-test", "\"hello there\"");

// Install a new template template: test.HelmInstall(yourInstallName); Deleted
// for Now
