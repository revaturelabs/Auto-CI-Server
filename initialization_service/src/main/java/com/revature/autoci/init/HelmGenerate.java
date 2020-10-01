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

/** 
 * The method chartGenerate generates a new file structure for helm chart, and print out the status on command
 * The method must specifi the below parameter
 * 
 * chartName        a name for the chart, which will be reflected in the name of the file and chart.yaml
 * apiVersion       the API version, v2, v1... which will be reflected in chart.yaml
 * type             the type of the helm chart, such as application, also reflected in chart.yaml
 * directoryToPush  the file directory for the chart being built
 * 
 * For example, this method shall be 
 * HelmGenerate test = new HelmGenerate();
 * test.chartGenerate("testChart", "v2", "application","C:/Users/xxx/");
 * 
 */

public class HelmGenerate {
    static final String DEFAULT_CHART_VERSION = "v2";
    static final String DEFAULT_CHART_TYPE = "application";
    
    public static void generateHelmChart(String chartName, String apiVersion, String chartType, String directoryToPush, boolean verbose)
    {
        chartGenerate(chartName, apiVersion, chartType, directoryToPush, verbose);
    }
    public static void generateHelmChart(String chartName, String chartType, String directoryToPush, boolean verbose)
    {
        chartGenerate(chartName, DEFAULT_CHART_VERSION, chartType, directoryToPush, verbose);
    }

    public static void generateHelmChart(String chartName, String directoryToPush, boolean verbose)
    {
        chartGenerate(chartName, DEFAULT_CHART_VERSION, DEFAULT_CHART_TYPE, directoryToPush, verbose);
    }

    private static void chartGenerate(String chartName, String apiVersion, String chartType, String directoryToPush, boolean verbose) {

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
                if(verbose)
                {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if(verbose)
            {
                System.out.println("\nBuilt with " + exitCode + " error(s).");
            }
            
            String path  = Paths.get(directoryToPush, chartName, "Chart.yaml").toString();
            File newchart = new File(path);
            FileReader fr = new FileReader(newchart);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.contains("description: A Helm chart for Kubernetes"))
                    line = line.replace("description: A Helm chart for Kubernetes",
                            "description: This is a sample chart for project " + chartName);
                lines.add(line);
            }
            while ((line = br.readLine()) != null) {
                if (line.contains("apiVersion: v2"))
                    line = line.replace("apiVersion: v2", "apiVersion: " + apiVersion);
                lines.add(line);
            }
            while ((line = br.readLine()) != null) {
                if (line.contains("type: application"))
                    line = line.replace("type: application", "type: " + chartType);
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

    /** 
    * The method helmTemplates generate a new template yaml file under the template file in the helm chart for helm to install
    * This method must speficy the parameters below
    *
    * chartName
    * apiVersion
    * kind
    * templateName
    * data
    * directoryToPush
    * 
    * For example, the method should look like 
    * test.helmTemplates("testChart", "v1", "ConfigMap", "configmap-test", "\"hello there\"", "C:/Users/xxx/");* 
    */
 
    private static void helmTemplates(String chartName, String apiVersion, String kind, String templateName,
            String data, String directoryToPush) {
        String filepath = Paths.get(directoryToPush, chartName, "templates", templateName+".yaml" ).toString();

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
// Generating a new Helm Chart with templaate: test.generateHelmChart("testChart", "v2",
// "application", directory);

// Install a new template template: test.HelmInstall(yourInstallName); Deleted
// for Now
