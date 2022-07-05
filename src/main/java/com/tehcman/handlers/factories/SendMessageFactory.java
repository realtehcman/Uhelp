package com.tehcman.handlers.factories;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface SendMessageFactory {
    SendMessage createSendMessage(Message message);
}