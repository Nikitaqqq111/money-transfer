package org.mycompany.utils;

import java.io.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

public class JSONUtils {

    private static final JSONParser jsonParser = new JSONParser();

    public static Object read(String fileName) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:" + fileName);
        try (FileReader reader = new FileReader(file)) {
            return jsonParser.parse(reader);
        } catch (Exception e) {
            System.err.println("Cannot download JSON from file: " + fileName);
            e.printStackTrace();
        }
        return null;
    }

    public static Object read(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.err.println("Error during handle input stream");
            e.printStackTrace();
        }

        try {
            return jsonParser.parse(sb.toString());
        } catch (ParseException e) {
            System.err.println("Error during parse json string");
            e.printStackTrace();
        }
        return null;
    }
}
