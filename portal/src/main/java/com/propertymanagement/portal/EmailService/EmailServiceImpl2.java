package com.propertymanagement.portal.EmailService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailServiceImpl2 {

    private final Session session;

    public EmailServiceImpl2() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        this.session = Session.getInstance(properties);
    }

    public void sendEmail(String toAddress, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("agabaivan024@gmail.com")); // Replace with your sender email address
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message, "agabaivan124@gmail.com", "Hospital1!"); // Replace with your sender email and app password
    }
}
