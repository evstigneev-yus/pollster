package com.lanimal.pollster;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandHandler {
    @Inject
    public CommandHandler() {
    }

    public void handleCommands(String commandText) {
        throw new UnsupportedOperationException();
    }
}
