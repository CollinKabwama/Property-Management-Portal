package com.propertymanagement.portal.EmailService;

import java.io.IOException;

public interface EmailService {

    public void sendEmail(String to_address, String subject, String body) throws IOException;
}
