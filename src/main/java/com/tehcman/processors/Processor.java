package com.tehcman.processors;

import com.tehcman.cahce.Cache;
import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Phase;
import com.tehcman.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor {

    abstract void handleText(Update update);

    abstract void handleCallBackQuery(CallbackQuery update);

    abstract void handleSaveToCache(Message message);

    private UserCache userCache;

    @Autowired
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    public void direct(Update update) {
        //active registration
        if (update.hasCallbackQuery()) {
            handleCallBackQuery(update.getCallbackQuery());
        } else {
            User userFromCache = userCache.findBy(update.getMessage().getChatId());
            if ((userFromCache != null) && !userFromCache.getPhase().equals(Phase.NONE)) {
/*                switch (userFromCache.getPosition()) {
                    case PHONE_NUMBER:
                    case AGE:*/
                        handleSaveToCache(update.getMessage());
                        return; //so it won't go to if lines
//                }
            }
        }
        if ((update.getMessage() != null) && (update.getMessage().getText() != null) && (update.getMessage().getText().equals("Accommodation search/hosting"))) {
            handleSaveToCache(update.getMessage());
        } else if ((update.getMessage() != null) && (update.getMessage().getText() != null)) {
            handleText(update);
        }
    }
}
