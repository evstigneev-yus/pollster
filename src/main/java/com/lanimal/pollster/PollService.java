package com.lanimal.pollster;

import com.lanimal.pollster.dto.PollDTO;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PollService {

    @Inject
    public PollService() {
    }

    public void startPoll(String chatId) {
//        todo: call createSendPoll
//        todo: execute sendPoll request
        throw new UnsupportedOperationException();
    }

    SendPoll createSendPoll(String chatId) {
//todo: create sendPoll object
        throw new UnsupportedOperationException();
    }

    PollDTO readPollTemplate() {
        //todo: read example file form config folder
        throw new UnsupportedOperationException();
    }
}
