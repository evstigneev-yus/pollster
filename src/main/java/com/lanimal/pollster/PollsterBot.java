package com.lanimal.pollster;

import lombok.AccessLevel;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PollsterBot extends TelegramLongPollingBot {

    @Inject
    Configuration configuration;

    @Inject
    @Setter(AccessLevel.PACKAGE)
    CommandHandler commandHandler;

    @Inject
    public PollsterBot(Configuration configuration) {
        super(configuration.getBotToken());
        System.out.println("Bot Successfully Started.");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() == null) {
            return;
        }
        if (update.getMessage().isCommand()) {
            commandHandler.handleCommands(update.getMessage().getText());
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return configuration.getBotName();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
