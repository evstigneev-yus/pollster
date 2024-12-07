package com.lanimal.pollster;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Singleton
public class Configuration {

    Properties properties = new Properties();

    @Inject
    public Configuration() {
        try (InputStream inputStream = Files.newInputStream(Paths.get(".env"))) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBotToken() {
        return properties.getProperty("TOKEN", null);
    }

    public String getBotName() {
        return properties.getProperty("NAME", null);
    }
}
