package com.lanimal.pollster;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Configuration {

    @Inject
    public Configuration() {
    }

    public String getPollsterDbUrl() {
//        jdbc:mysql://123456789:3306;database=pollster"
        throw new UnsupportedOperationException();
    }

    public String getDbUserName() {
        throw new UnsupportedOperationException();
    }

    public String getDbPassword() {
        throw new UnsupportedOperationException();
    }

    public String getBotToken() {
        throw new UnsupportedOperationException();
    }
}
