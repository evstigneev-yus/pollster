package com.lanimal.pollster;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PollsterBot extends TelegramLongPollingBot {

    @Inject
    public PollsterBot() {
        super("TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        // here will be processing of stop conditions for polls

        //here will be commands processing like:
        // create scheduled poll
        // delete scheduled poll
        // list scheduled polls
        // list answers
        // edit poll
        // edit schedule
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "true_pollster_bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
        //start scheduler(read all schedules form db for next day and )
        //consider quartz https://mvnrepository.com/artifact/org.quartz-scheduler/quartz
    }
}
