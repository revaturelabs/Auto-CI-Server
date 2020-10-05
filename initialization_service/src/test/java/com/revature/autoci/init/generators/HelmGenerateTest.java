package com.revature.autoci.init.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class HelmGenerateTest {
    static Path tempDir;
    @Before
    public void setup() throws IOException
    {
        tempDir = Files.createTempDirectory("hgt");
    }

    @Test
    public void lintTest() throws IOException, InterruptedException
    {
        HelmGenerate.generateHelmChart("example", "1.0.0", "example/example", tempDir.toString(), false);

        ProcessBuilder pb = new ProcessBuilder("helm", "lint", "example");
        pb.directory(tempDir.toFile());
        Process p = pb.start();
        
        assertTrue(p.waitFor(3, TimeUnit.SECONDS));
        assertEquals(0, p.exitValue());
    }

    @Test
    public void testDirectoryStructure() throws IOException
    {
        HelmGenerate.generateHelmChart("example", "1.0.0", "example/example", tempDir.toString(), false);

        Path baseDir = Paths.get(tempDir.toString(), "example");
        assertTrue(baseDir.toFile().isDirectory());
        assertTrue(Paths.get(baseDir.toString(), ".helmignore").toFile().isFile());
        assertTrue(Paths.get(baseDir.toString(), "Chart.yaml").toFile().isFile());
        assertTrue(Paths.get(baseDir.toString(), "values.yaml").toFile().isFile());
        assertTrue(Paths.get(baseDir.toString(), "charts").toFile().isDirectory());
        assertTrue(Paths.get(baseDir.toString(), "templates").toFile().isDirectory());
        assertTrue(Paths.get(baseDir.toString(), "templates", "deployment.yaml").toFile().isFile());
        assertTrue(Paths.get(baseDir.toString(), "templates", "service.yaml").toFile().isFile());
        assertTrue(Paths.get(baseDir.toString(), "templates").toFile().isDirectory());
        assertTrue(Paths.get(baseDir.toString(), "templates", "tests").toFile().isDirectory());
    }

    @Test
    public void testChartYamlContents() throws IOException
    {
        HelmGenerate.generateHelmChart("example", "1.0.0", "example/example", tempDir.toString(), false);
        String chart = new String(Files.readAllBytes(Paths.get(tempDir.toString(), "example", "Chart.yaml")), UTF_8); 
        assertTrue(chart.contains("apiVersion: v2"));
        assertTrue(chart.contains("name: example"));
        assertTrue(chart.contains("type: application"));
        assertTrue(chart.contains("appVersion: 1.0.0"));
    }

    @Test
    public void testValuesYamlContents() throws IOException
    {
        HelmGenerate.generateHelmChart("example", "1.0.0", "example/example", tempDir.toString(), false);
        String values = new String(Files.readAllBytes(Paths.get(tempDir.toString(), "example", "values.yaml")), UTF_8);
        assertTrue(values.contains("repository: example/example"));
        assertTrue(values.contains("type: LoadBalancer"));
        assertTrue(values.contains("port: 80"));
        assertTrue(values.contains("targetPort: 8080"));
    }

}
