package com.lanimal.pollster.di;

import dagger.Module;
import dagger.Provides;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.inject.Singleton;

@Module
public class TelegramBotApiModule {

    @Provides
    @Singleton
    public TelegramBotsApi provideTelegramBotsApi() {
        try {
            return new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
