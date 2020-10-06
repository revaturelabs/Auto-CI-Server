package com.revature.autoci.init.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.revature.autoci.init.generators.utils.Templater;

import org.apache.commons.lang3.arch.Processor.Arch;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

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
    /**
     * Generate a basic helm chart with the specified information.
     * @param chartName The name of the helm chart
     * @param apiVersion The helm chart format version
     * @param appVersion The version of the application
     * @param chartType The type of chart (application or library)
     * @param imageRepo The repository containing the image for this application.
     * @param directoryToPush The directory where the helm chart directory will be created.
     * @param verbose Sets whether or not to print debug statements (Deprecated)
     * @throws IOException
     */
    public static void generateHelmChart(String chartName, String apiVersion, String appVersion, 
     String chartType, String imageRepo, String directoryToPush, boolean verbose) throws IOException
    {
        chartGenerate(chartName, apiVersion, appVersion, chartType, imageRepo, directoryToPush, verbose);
    }
    /**
     * Generate a basic helm chart with the specified information.
     * @param chartName The name of the helm chart
     * @param appVersion The version of the application
     * @param chartType The type of chart (application or library)
     * @param imageRepo The repository containing the image for this application.
     * @param directoryToPush The directory where the helm chart directory will be created.
     * @param verbose Sets whether or not to print debug statements (Deprecated)
     * @throws IOException
     */
    public static void generateHelmChart(String chartName, String appVersion, 
     String chartType, String imageRepo, String directoryToPush, boolean verbose) throws IOException
    {
        chartGenerate(chartName, DEFAULT_CHART_VERSION, appVersion, chartType, imageRepo, directoryToPush, verbose);
    }

    /**
     * Generate a basic helm chart with the specified information.
     * @param chartName The name of the helm chart
     * @param appVersion The version of the application
     * @param imageRepo The repository containing the image for this application.
     * @param directoryToPush The directory where the helm chart directory will be created.
     * @param verbose Sets whether or not to print debug statements (Deprecated)
     * @throws IOException
     */
    public static void generateHelmChart(String chartName, String appVersion, String imageRepo, String directoryToPush, boolean verbose) throws IOException
    {
        chartGenerate(chartName, DEFAULT_CHART_VERSION, appVersion, DEFAULT_CHART_TYPE, imageRepo, directoryToPush, verbose);
    }

    private static void chartGenerate(String chartName, String apiVersion, String appVersion, 
     String chartType, String imageRepo, String directoryToPush, boolean verbose) throws IOException {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("chart_api_version", apiVersion);
        mapping.put("project_name", chartName);
        mapping.put("chart_type", chartType);
        mapping.put("version", appVersion);
        mapping.put("image_repo", imageRepo);

        Templater templater = new Templater(mapping);

        // Create helm chart dir
        Path helmDir = Paths.get(directoryToPush, chartName);
        if(helmDir.toFile().mkdir() == false)
        {
            throw new IOException("Chart directory already exists");
        }
                
        String template = "Helm/Chart.yaml";
        ClassLoader loader = HelmGenerate.class.getClassLoader();

        // Fill in the template and create new Jenkinsfile
        InputStream templateStream = loader.getResourceAsStream(template);
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream))) {
            File chartYaml = Paths.get(helmDir.toString(), "Chart.yaml").toFile();
            chartYaml.createNewFile();
            try (FileWriter writer = new FileWriter(chartYaml)) {
                templater.fillTemplate(buf, writer, "");
            }
        }

        template = "Helm/values.yaml";
        templateStream = loader.getResourceAsStream(template);
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream))) {
            File chartYaml = Paths.get(helmDir.toString(), "values.yaml").toFile();
            chartYaml.createNewFile();
            try (FileWriter writer = new FileWriter(chartYaml)) {
                templater.fillTemplate(buf, writer, "");
            }
        }

        Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR);
        archiver.extract(loader.getResourceAsStream("Helm/helm.tar"), helmDir.toFile());
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