package com.lanimal.pollster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.*;

class PollsterBotTest {

    Configuration mockConfiguration;

    CommandHandler mockCommandHandler;

    PollsterBot pollsterBot;


    @BeforeEach
    public void before() {
        mockCommandHandler = mock(CommandHandler.class);
        mockConfiguration = mock(Configuration.class);
        pollsterBot = spy(new PollsterBot(mockConfiguration));
        pollsterBot.setCommandHandler(mockCommandHandler);
    }


    @Test
    public void onUpdateReceived_callCommandHandlerHandleCommands() {
        // setup
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.isCommand()).thenReturn(true);
        when(mockMessage.getText()).thenReturn("/test_command_1");

        // act
        pollsterBot.onUpdateReceived(mockUpdate);

        // verify
        verify(mockCommandHandler).handleCommands("/test_command_1");
    }

    @Test
    public void onUpdateReceived_messageIsNull_doNotCallCommandHandlerHandleCommands() {
        // setup
        Update mockUpdate = mock(Update.class);
        when(mockUpdate.getMessage()).thenReturn(null);

        // act
        pollsterBot.onUpdateReceived(mockUpdate);

        // verify
        verify(mockCommandHandler, never()).handleCommands(any());
    }

    @Test
    public void onUpdateReceived_messageIsCommandFalse_doNotCallCommandHandlerHandleCommands() {
        // setup
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.isCommand()).thenReturn(false);

        // act
        pollsterBot.onUpdateReceived(mockUpdate);

        // verify
        verify(mockCommandHandler, never()).handleCommands(any());
    }

}