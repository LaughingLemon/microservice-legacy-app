/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.user.integration;

import nl.conspect.legacy.mail.MailService;
import nl.conspect.legacy.mail.SendEmail;
import nl.conspect.legacy.user.User;
import nl.conspect.legacy.user.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Shaun
 */
@Component
public class UserMailListener implements ApplicationListener<UserCreatedEvent> {
    
    private final MailService mailService;

    @Autowired
    public UserMailListener(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(UserCreatedEvent e) {
        final User user = e.getUser();
        String body = "Welcome new user: " + user.getDisplayName();
        mailService.sendEmail(new SendEmail("New User", user.getEmailAddress(), body));
    }
    
}
