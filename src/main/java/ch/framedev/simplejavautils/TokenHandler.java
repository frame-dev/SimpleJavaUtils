package ch.framedev.simplejavautils;

/*
 * ch.framedev.simplejavautils
 * =============================================
 * This File was Created by FrameDev
 * Please do not change anything without my consent!
 * =============================================
 * This Class was created at 12.05.2024 15:50
 */

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("unused")
public class TokenHandler {

    private Properties properties;
    private final File file;
    private final SimpleJavaUtils utils = new SimpleJavaUtils();
    private final String fileName;

    public TokenHandler(String fileName) {
        this.fileName = fileName;
        this.file = utils.getFromResourceFile(fileName, Main.class);
        this.file.deleteOnExit();
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() throws IOException {
        Properties resourceProperties = new Properties();
        resourceProperties.load(new FileReader(utils.getFromResourceFile(fileName, Main.class)));
        this.properties = new Properties();
        this.properties.load(new FileReader(file));
        resourceProperties.forEach((key, value) -> {
            if (!properties.containsKey(key))
                properties.put(key, value);
        });
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public boolean contains(String key) {
        return this.properties.containsKey(key);
    }
}