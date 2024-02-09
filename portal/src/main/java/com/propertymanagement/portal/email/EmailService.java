package com.propertymanagement.portal.email;

public interface EmailService {
    public void sendEmail(String to, String subject, String text);
}
