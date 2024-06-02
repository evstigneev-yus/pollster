package com.lanimal.pollster;

import com.lanimal.pollster.di.DaggerMainComponent;
import com.lanimal.pollster.di.MainComponent;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static final MainComponent MAIN_COMPONENT = DaggerMainComponent.builder()
            .build();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = MAIN_COMPONENT.telegramBotsApi();
        botsApi.registerBot(MAIN_COMPONENT.pollsterBot());
    }
}
