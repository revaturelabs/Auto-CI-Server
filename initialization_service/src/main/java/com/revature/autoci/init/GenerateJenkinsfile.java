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

public class GenerateJenkinsfile {
    
    public static void generateJenkinsfile(String githubURL, String dockerUser, String projectName, String credentialId, String pathToProject) throws IOException
    {
        Map<String, String> pairs = new HashMap<>();
        pairs.put("githubURL", githubURL);
        pairs.put("dockerUser", dockerUser);
        pairs.put("projectName", projectName);
        pairs.put("ContainerRepoCredId", credentialId);
        Map<Pattern, String> replaceVals = compileRegex(pairs);

        InputStream templateStream = GenerateJenkinsfile.class.getClassLoader().getResourceAsStream("Jenkinsfile_templates/Jenkinsfile");
        try(BufferedReader buf = new BufferedReader(new InputStreamReader(templateStream)))       
        {
            File jenkinsfile = Paths.get(pathToProject,"Jenkinsfile").toFile();
            jenkinsfile.createNewFile();
            try(FileWriter writer = new FileWriter(jenkinsfile))
            {
                replaceVariablesInFile(buf, writer, replaceVals);
            }
        }
    }

    private static Map<Pattern, String> compileRegex(Map<String, String> pairs)
    {
        Map<Pattern, String> map = new HashMap<>();
        for(Entry<String, String> pair: pairs.entrySet())
        {
            Pattern p = Pattern.compile("\\$\\{\\s*"+ pair.getKey() + "\\s*\\}");
            map.put(p, pair.getValue());
        }
        return map;
    }

    private static void replaceVariablesInFile(BufferedReader reader, Writer writer, Map<Pattern, String> map) throws IOException
    {
        while(reader.ready())
        {
            String s = reader.readLine();
            for(Entry<Pattern, String> entry: map.entrySet())
            {
                Matcher m = entry.getKey().matcher(s);
                if(m.find())
                {
                    s = m.replaceAll("\'"+entry.getValue() +"\'");
                }
            }
            writer.write(s + "\n");
        }
    }
}
