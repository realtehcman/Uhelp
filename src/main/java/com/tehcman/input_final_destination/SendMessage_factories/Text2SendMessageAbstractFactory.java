package com.tehcman.input_final_destination.SendMessage_factories;
//Abstract Factory Method Pattern

import com.tehcman.cahce.Cache;
import com.tehcman.entities.Position;
import com.tehcman.entities.User;
import com.tehcman.informational_portal.IListOfNewsChannels;
import com.tehcman.informational_portal.ListOfNewsChannels;
import com.tehcman.services.BuildButtonsService;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.keyboards.AfterRegistrationKeyboard;
import com.tehcman.services.keyboards.BeforeRegistrationKeyboard;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Component
public class Text2SendMessageAbstractFactory implements SendMessageAbstractFactory {
    private final Cache<User> userCache;
    private final IBuildSendMessageService buildSendMessageService;
    private final IListOfNewsChannels iListOfNewsChannels;

    @Autowired
    public Text2SendMessageAbstractFactory(IBuildSendMessageService buildSendMessageService, Cache<User> userCache) {
        this.buildSendMessageService = buildSendMessageService;
        this.userCache = userCache;
        this.iListOfNewsChannels = new ListOfNewsChannels();
    }

    @Override
    public SendMessage create1stSendMessage(Message message) {
        if (message.getText().equals("List of news channels about Ukraine (ENG)")) {
            //TODO: POSSIBLE REFACTORING. apply decorator pattern to build message sender

            var newMsg = this.buildSendMessageService.createHTMLMessage(message.getChatId().toString(), iListOfNewsChannels.getMapDescription(), returnReplyMarkup(message).getMainMarkup());
            return newMsg;
        } else throw new ArgumentAccessException("reached unhandled case");
    }

    @Override
    public SendMessage create2ndSendMessage(Message message) {
        if (message.getText().equals("List of news channels about Ukraine (ENG)")) {

            String formattedString = "";
            Map<String, String> map = iListOfNewsChannels.getMapOfChannelsAndLinks();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formattedString += "[" + (entry.getKey()) + "]" + "(" + entry.getValue() + ")\n";
            }
            var newMsg = SendMessage.builder()
                    .text(formattedString)
                    .chatId(message.getChatId().toString())
                    .replyMarkup(returnReplyMarkup(message).getMainMarkup())
                    .disableWebPagePreview(Boolean.TRUE)
                    .parseMode("MarkdownV2")
                    .build();
            return newMsg;
        } else throw new ArgumentAccessException("reached unhandled case");
    }

    private BuildButtonsService returnReplyMarkup(Message message) {
        User userFromCache = userCache.findBy(message.getChatId());
        if ((userFromCache != null) && userFromCache.getPosition().equals(Position.NONE)) {
            return new BuildButtonsService(new AfterRegistrationKeyboard());
        }
        return new BuildButtonsService(new BeforeRegistrationKeyboard());
    }
}
