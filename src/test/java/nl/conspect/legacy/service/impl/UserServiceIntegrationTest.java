/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.service.impl;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import nl.conspect.legacy.RemoteSystemEmulator;
import nl.conspect.legacy.user.User;
import nl.conspect.legacy.user.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 *
 * @author Shaun
 */
@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext.xml"})
public class UserServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private GreenMail greenMail;
    private RemoteSystemEmulator emulator;

    @Autowired
    private UserService userService;

    @Before
    public void setup() throws IOException {
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();

        emulator = new RemoteSystemEmulator();
        emulator.start();
    }

    @After
    public void cleanup() throws IOException {
        greenMail.stop();
        emulator.stop();
    }

    @Test
    public void testNewUser() throws InterruptedException, IOException, MessagingException {
        User user = new User();
        user.setDisplayName("Display Name");
        user.setEmailAddress("email@address.com");
        user.setUsername("username");
        user.setPassword("password");

        userService.save(user);

        Thread.sleep(1000);
        
        int count = countRowsInTable("user");
        assertThat(count).isEqualTo(1);
        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        assertThat(mimeMessages.length).isEqualTo(1);
        MimeMessage message = mimeMessages[0];
        assertThat(message.getContent()).asString().contains("Welcome new user: Display Name");

        assertThat(emulator.getReceived()).contains("username:username|email:email@address.com");
    }

    @Test
    public void testUpdateUser() throws InterruptedException {
        User user = new User();
        user.setDisplayName("Display Name");
        user.setEmailAddress("email@address.com");
        user.setUsername("username");
        user.setPassword("password");

        userService.update(user);

        Thread.sleep(1000);

        int count = countRowsInTable("user");
        assertThat(count).isEqualTo(1);

        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        assertThat(mimeMessages.length).isEqualTo(0);

        assertThat(emulator.getReceived()).isNotEmpty().contains("username:username|email:email@address.com");
    }
}
