package com.tehcman.printers;

import com.tehcman.cahce.UserCache;
import com.tehcman.entities.Status;
import com.tehcman.entities.User;
import com.tehcman.sendmessage.MessageSender;
import com.tehcman.services.IBuildSendMessageService;
import com.tehcman.services.ParsingJSONtoListService;
import com.tehcman.services.keyboards.profile_search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HostProfile implements IPrintUserProfile {
    private List<User> hosts;
    private int prevNumber = -1;
    private final UserCache userCache;
    private final MessageSender messageSender;
    private final IBuildSendMessageService iBuildSendMessageService;

    //keyboards
    private final InlineNewProfilesNotification inlineNewProfilesNotification;
    private final InlineNoProfiles inlineNoProfiles;
    private final InlineProfileNavigation inlineProfileNavigation;

    @Autowired
    public HostProfile(UserCache userCache, MessageSender messageSender, IBuildSendMessageService iBuildSendMessageService, InlineNewProfilesNotification inlineNewProfilesNotification, InlineNoProfiles inlineNoProfiles, InlineProfileNavigation inlineProfileNavigation) {
        this.hosts = new ArrayList<>();
//        setHostsFromCache();

        //fetching data from json
        ParsingJSONtoListService parsingJSONtoListService = new ParsingJSONtoListService();

        ArrayList<User> hosts = (ArrayList<User>) filterUsers(parsingJSONtoListService.parse(), Status.HOST);
        this.hosts.addAll(hosts);
        hosts.forEach(System.out::println);

        this.userCache = userCache;
        this.messageSender = messageSender;
        this.iBuildSendMessageService = iBuildSendMessageService;
        this.inlineNewProfilesNotification = inlineNewProfilesNotification;
        this.inlineNoProfiles = inlineNoProfiles;
        this.inlineProfileNavigation = inlineProfileNavigation;
    }

    @Override
    public void setUsersFromCache() {
        if ((userCache != null) && (userCache.getAll().size() > 0)) {
            this.hosts.addAll(userCache.getAll().stream()
                    .filter(x -> x.getStatus().equals(Status.HOST))
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void viewedAllUsers(Message msg) {
        //if boolean equals the size of the array, it has been viewed by a user

    }

    @Override
    public void notifyNewProfiles(Message msg) {
        //if tracked number is less than the size of 10, notify the user

    }

    @Override
    public List<User> filterUsers(List<User> userList, Status whoToLookFor) {
        return userList.stream()
                .filter(x -> x.getStatus().equals(whoToLookFor))
                .collect(Collectors.toList());
    }

    @Override
    public void printUserRandomDefault(Message msg) {
        int randNumb = (int) (Math.random() * this.hosts.size());
        while (randNumb == prevNumber) {
            randNumb = (int) (Math.random() * this.hosts.size());
        }
        prevNumber = randNumb;

        SendMessage newMessage = iBuildSendMessageService.createHTMLMessage(msg.getChatId().toString(),
                this.hosts.get(prevNumber).toString(), inlineProfileNavigation.getMainMarkup());
        messageSender.messageSend(newMessage);
    }

    @Override
    public String beautify(Long id) {
        return null;
    }
}