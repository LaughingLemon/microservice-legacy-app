/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.remote;

import java.io.IOException;
import nl.conspect.legacy.user.User;

/**
 *
 * @author Shaun
 */
public class RemoteSystemSynchronizer {

    public void synchronize(User user) {
        Thread syncThread = new Thread(() -> {
            String msg = "username:" + user.getUsername() + "|email:" + user.getEmailAddress() + "$";
            try {
                new RemoteSystemClient().send(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        syncThread.start();
    }

}
