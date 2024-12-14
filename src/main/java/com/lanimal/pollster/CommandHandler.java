package com.lanimal.pollster;

import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandHandler {
    @Inject
    public CommandHandler() {
    }

    public void handleCommands(Update update) {
        //todo: on "/start" command call pollService.startPoll
        throw new UnsupportedOperationException();
    }
}
