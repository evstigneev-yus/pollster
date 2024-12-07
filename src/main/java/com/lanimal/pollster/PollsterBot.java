package com.lanimal.pollster;

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
    public PollsterBot(Configuration configuration) {
        super(configuration.getBotToken());
        System.out.println("Bot Successfully Started.");
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        System.out.println(text);
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
