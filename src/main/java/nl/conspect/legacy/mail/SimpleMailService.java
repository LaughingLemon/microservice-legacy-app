/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Shaun
 */
class SimpleMailService implements MailService {

    @Override
    public void sendEmail(SendEmail sendEmail) {
        Thread mailThread = new Thread(() -> {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "localhost");
            props.setProperty("mail.smtp.port", "2525");
            Session session = Session.getInstance(props);
            
            try {
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("noreply@ourcompany.io"));
                msg.setText(sendEmail.getBody(), "UTF-8");
                msg.addRecipients(Message.RecipientType.TO, sendEmail.getRecipient());
                msg.setSubject(sendEmail.getSubject());
                Transport.send(msg);
            } catch (MessagingException me) {
                throw new RuntimeException(me);
            }
        });
        mailThread.start();
    }
    
}
