package com.cupidconnect.cupidconnect.controllers.demo;

import com.cupidconnect.cupidconnect.dtos.EmailDTO;
import com.cupidconnect.cupidconnect.services.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("${api.prefix}")
public class DemoEmailController {

    private final EmailServiceImpl emailService;

    @PostMapping("/send-mail-otp")
    public ResponseEntity<?> sendMail(@RequestBody EmailDTO email) {
        Context context = new Context();
        context.setVariable("message", email.getMessage());
        return emailService.sendEmail(email, "email-template", context);
    }

    @PostMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(@ModelAttribute EmailDTO email) throws MessagingException {
        return emailService.sendMailWithAttachment(email);
    }

}
