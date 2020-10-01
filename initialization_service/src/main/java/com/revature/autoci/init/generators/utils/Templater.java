package com.revature.autoci.init.generators.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple templating class. Takes in a map of variable names to values, then replaces instances of ${variable_name}.
 */
public class Templater {
    private Map<Pattern, String> regexMap;

    public Templater(Map<String, String> mapping)
    {
        regexMap = compileRegex(mapping);
    }

    public void fillTemplate(BufferedReader reader, Writer writer, String wrapString)
            throws IOException {
        while (reader.ready()) {
            String s = reader.readLine();
            for (Entry<Pattern, String> entry : regexMap.entrySet()) {
                Matcher m = entry.getKey().matcher(s);
                if (m.find()) {
                    s = m.replaceAll(wrapString + entry.getValue() + wrapString);
                }
            }
            writer.write(s + "\n");
        }
    }

    private Map<Pattern, String> compileRegex(Map<String, String> pairs) {
        Map<Pattern, String> map = new HashMap<>();
        for (Entry<String, String> pair : pairs.entrySet()) {
            Pattern p = Pattern.compile("\\$\\{\\s*" + pair.getKey() + "\\s*\\}");
            map.put(p, pair.getValue());
        }
        return map;
    }

}
