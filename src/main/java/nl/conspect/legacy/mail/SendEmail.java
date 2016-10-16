/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.mail;

/**
 *
 * @author Shaun
 */
public class SendEmail {

    private final String subject;
    private final String recipient;
    private final String body;

    public SendEmail(final String newSubject, 
                     final String newRecipient, 
                     final String newBody) {
        this.subject = newSubject;
        this.recipient = newRecipient;
        this.body = newBody;
    }

    public String getSubject() {
        return subject;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

}
