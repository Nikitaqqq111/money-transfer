package org.mycompany.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import org.springframework.util.ResourceUtils;

public class JSONUtils {
    public static JSONAware read(String fileName) throws FileNotFoundException {
        JSONParser jsonParser = new JSONParser();
        File file = ResourceUtils.getFile("classpath:" + fileName);
        try (FileReader reader = new FileReader(file)) {
            JSONAware jsonObject = (JSONAware) jsonParser.parse(reader);
            System.out.println("downloaded JSON: " + jsonObject);
            return jsonObject;
        } catch (Exception e) {
            System.err.println("Cannot download JSON from file: " + fileName);
            e.printStackTrace();
        }
        return null;
    }
}
