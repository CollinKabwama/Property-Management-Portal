package com.propertymanagement.portal.EmailService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailServiceImpl {

    private final Session session;

    public EmailServiceImpl() {
        // Create a default session with no additional properties
        this.session = Session.getDefaultInstance(new Properties());
    }

    public void sendEmail(String toAddress, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("agabaivan024@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
