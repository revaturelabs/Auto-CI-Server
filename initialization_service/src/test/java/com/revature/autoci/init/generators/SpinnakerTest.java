package com.revature.autoci.init.generators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.junit.Before;
import org.junit.Test;

/**
 * Performs some basic tests on the Spinnaker generation.
 * These tests are not comprehensive, and should not be used as the only 
 * indication of valid Spinnaker.json generation.
 * @throws IOException
 */
public class SpinnakerTest {
    Path tempPath;
    @Before
    public void setup() throws IOException
    {
        tempPath = Files.createTempDirectory("jkt");
        tempPath.toFile().deleteOnExit();
    }

    @Test
    public void createsFile() throws IOException
    {
        GenerateSpinnaker.generateSpinnaker("example", tempPath.toString(), "example");
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Spinnaker.json")));

        tempPath = Files.createTempDirectory("jkt");
        tempPath.toFile().deleteOnExit();
        GenerateSpinnaker.generateSpinnaker("example2", tempPath.toString(), "example2");
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "Spinnaker.json")));
    }

    @Test
    public void jsonValidate() throws IOException
    {
        GenerateSpinnaker.generateSpinnaker("example", tempPath.toString(), "example");
        
        byte[] bytes = Files.readAllBytes(Paths.get(tempPath.toString(), "Spinnaker.json"));

        JsonParser.parseReader(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.UTF_8));
    }

    @Test
    public void replacesAll() throws IOException
    {
        GenerateSpinnaker.generateSpinnaker("example", tempPath.toString(), "example");
        Pattern p = Pattern.compile("\\$\\{\\s*[a-zA-Z0-9]*\\s*\\}");
        
        String s = new String(Files.readAllBytes(Paths.get(tempPath.toString(), "Spinnaker.json")));
        assertFalse(p.matcher(s).find());
    }
}
