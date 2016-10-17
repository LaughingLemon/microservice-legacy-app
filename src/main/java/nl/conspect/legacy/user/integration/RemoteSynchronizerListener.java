/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.user.integration;

import java.io.IOException;
import nl.conspect.legacy.remote.RemoteSystemClient;
import nl.conspect.legacy.user.AbstractUserEvent;
import nl.conspect.legacy.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author Shaun
 */
@Component
public class RemoteSynchronizerListener implements ApplicationListener<AbstractUserEvent> {

    private final RemoteSystemClient remoteSystemClient;

    @Autowired
    public RemoteSynchronizerListener(RemoteSystemClient remoteSystemClient) {
        this.remoteSystemClient = remoteSystemClient;
    }
    
    private void synchronizeRemoteClient(final User user) {
        String msg = "username:" + user.getUsername() + "|email:" + user.getEmailAddress() + "$";
        try {
            remoteSystemClient.send(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    @Async
    public void onApplicationEvent(AbstractUserEvent e) {
        this.synchronizeRemoteClient(e.getUser());
    }
    
}
