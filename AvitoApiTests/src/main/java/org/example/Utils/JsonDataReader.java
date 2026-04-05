package org.example.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonDataReader {

    private JsonDataReader() {
    }

    public static String readJson(String path) {
        try (InputStream inputStream = JsonDataReader.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Файл не найден: " + path);
            }
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать json: " + path, e);
        }
    }

    public static String readJsonAndReplace(String path, Map<String, String> replacements) {
        String json = readJson(path);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            json = json.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return json;
    }
}
