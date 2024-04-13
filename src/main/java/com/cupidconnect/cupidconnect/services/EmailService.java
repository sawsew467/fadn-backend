package com.cupidconnect.cupidconnect.services;


import com.cupidconnect.cupidconnect.dtos.EmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.context.Context;

public interface EmailService {

    ResponseEntity<?> sendEmail(EmailDTO email, String templateName, Context context);
    String sendMailWithAttachment(EmailDTO email) throws MessagingException;

}
