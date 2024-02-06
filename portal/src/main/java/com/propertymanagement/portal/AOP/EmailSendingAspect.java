package com.propertymanagement.portal.AOP;

import com.propertymanagement.portal.EmailService.EmailService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
    @Component
    public class EmailSendingAspect {

        @Autowired
        private EmailService emailService; // Assume EmailService is a service class for sending emails

        @Pointcut("execution(* com.example.controller.UserInteractionController.sendEmailToOwner(..))")
        public void sendEmailToOwner() {}

        @Before("sendEmailToOwner()")
        public void beforeSendingEmailToOwner() {
            // Prepare email content
            String toAddress = "owner@example.com"; // Retrieve owner's email address
            String subject = "User Inquiry";
            String body = "This is an inquiry from a user.";

            // Send email to owner
            try {
                emailService.sendEmail(toAddress, subject, body);
                System.out.println("Email sent to owner: " + toAddress);
            } catch (IOException e) {
                System.err.println("Failed to send email to owner: " + e.getMessage());
            }
        }
    }


