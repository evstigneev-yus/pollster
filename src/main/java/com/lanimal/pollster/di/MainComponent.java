package com.lanimal.pollster.di;

import com.lanimal.pollster.PollsterBot;
import dagger.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MainModule.class, TelegramBotApiModule.class})
public interface MainComponent {

    PollsterBot pollsterBot();

    TelegramBotsApi telegramBotsApi();
}
