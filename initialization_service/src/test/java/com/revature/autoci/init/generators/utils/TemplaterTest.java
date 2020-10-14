package com.revature.autoci.init.generators.utils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TemplaterTest {
    static Path tempDir;
    static Map<String, String> mapping;
    @Before
    public void Setup() throws IOException
    {
        tempDir = Files.createTempDirectory("tt");
        tempDir.toFile().deleteOnExit();
        mapping = new HashMap<>();
    }

    @Test
    public void fillTest() throws IOException
    {
        String fileContents = "name : ${name}\n aaaaaaaaaaaaaaa ${id} aaaaaaaaaaaa\n";
        mapping.put("name", "Hello");
        mapping.put("id", "5643");
        Path template = Paths.get(tempDir.toString(), "template.txt");
        Files.write(template, fileContents.getBytes(UTF_8));
        Templater templater = new Templater(mapping);
        BufferedReader r = Files.newBufferedReader(template, UTF_8);

        Path resultPath = Paths.get(tempDir.toString(), "result.txt");
        BufferedWriter w = Files.newBufferedWriter(resultPath, UTF_8);
        
        templater.fillTemplate(r, w, "");
        r.close();
        w.close();

        String expected = "name : Hello\n aaaaaaaaaaaaaaa 5643 aaaaaaaaaaaa\n";
        String real = new String(Files.readAllBytes(resultPath), UTF_8);
        assertEquals(expected, real);
    }

    @Test
    public void missingFieldTest() throws IOException
    {
        String fileContents = "name : ${name}\n aaaaaaaaaaaaaaa ${id} aaaaaaaaaaaa\n";
        mapping.put("name", "Hello");
        Path template = Paths.get(tempDir.toString(), "template.txt");
        Files.write(template, fileContents.getBytes(UTF_8));
        Templater templater = new Templater(mapping);
        BufferedReader r = Files.newBufferedReader(template, UTF_8);

        Path resultPath = Paths.get(tempDir.toString(), "result.txt");
        BufferedWriter w = Files.newBufferedWriter(resultPath, UTF_8);
        
        templater.fillTemplate(r, w, "");
        r.close();
        w.close();

        String real = new String(Files.readAllBytes(resultPath), UTF_8);
        assertTrue(real.contains("${id}") && !real.contains("${name}"));
    }

}
