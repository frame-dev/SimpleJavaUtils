package ch.framedev.simplejavautils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("unused")
public class Settings {

    private Properties properties;
    private final File file;
    private String resource;
    private Class<?> clazz;

    public Settings(File file) {
        this.file = file;
        if(!file.exists()) {
            if(file.getParentFile() != null && !file.getParentFile().exists())
                if(!file.getParentFile().mkdir()) {
                    throw new IllegalStateException("Could not create directory : " + file.getParentFile().getAbsolutePath());
                }
            try {
                if(file.createNewFile()) {
                    throw new IllegalStateException("Could not create new File : " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Settings(String resource, Class<?> clazz, File file) {
        this.resource = resource;
        this.clazz = clazz;
        this.file = file;
        if(!file.exists()) {
            if(file.getParentFile() != null && !file.getParentFile().exists())
                if(!file.getParentFile().mkdir()) {
                    throw new IllegalStateException("Could not create directory : " + file.getParentFile().getAbsolutePath());
                }
            try {
                if(file.createNewFile()) {
                    throw new IllegalStateException("Could not create new File : " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() throws IOException {
        if(!file.exists()) {
            Properties resourceProperties = loadResouceProperties();
            this.properties = new Properties();
            properties.putAll(resourceProperties);
            this.properties.store(new FileWriter(file), "");
        } else {
            Properties resourceProperties = loadResouceProperties();
            this.properties = new Properties();
            this.properties.load(new FileReader(file));
            resourceProperties.forEach((key, value) ->  {
                if(!properties.containsKey(key))
                    properties.put(key, value);
            });
            this.properties.store(new FileWriter(file), "");
        }
    }

    public Properties loadResouceProperties() throws IOException {
        Properties resourceProperties = new Properties();
        if(resource == null) {
            resourceProperties.load(new FileReader(new SimpleJavaUtils().getFromResourceFile("settings.properties", SimpleJavaUtils.class)));
        } else {
            resourceProperties.load(new FileReader(new SimpleJavaUtils().getFromResourceFile(resource, clazz)));
        }
        return resourceProperties;
    }

    @SuppressWarnings("unused")
    public Properties getProperties() {
        return properties;
    }

    public void save() {
        try {
            this.properties.store(new FileWriter(file), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    @SuppressWarnings("unused")
    public boolean contains(String key) {
        return this.properties.containsKey(key);
    }
}