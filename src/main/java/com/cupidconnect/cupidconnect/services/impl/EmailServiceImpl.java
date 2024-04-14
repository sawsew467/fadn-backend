package com.cupidconnect.cupidconnect.services.impl;

import com.cupidconnect.cupidconnect.dtos.EmailDTO;
import com.cupidconnect.cupidconnect.dtos.ExceptionDTO;
import com.cupidconnect.cupidconnect.dtos.SuccessDTO;
import com.cupidconnect.cupidconnect.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserServiceImpl userService;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

//    public EmailServiceImpl(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    @Override
    public ResponseEntity<?> sendEmail(EmailDTO email, String templateName, Context context) {

        if (userService.isExistsByEmail(email.getRecipient())) {
            HttpStatus status = HttpStatus.ALREADY_REPORTED;
            ExceptionDTO exceptionDTO = new ExceptionDTO(
                    "Email already registered! Please register a new email!", // message
                    status.name(), // error
                    status.value(), // statusCode
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(exceptionDTO);
        }

        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom("Cupid Dating<"+sender+">");
//            mailMessage.setTo(email.getRecipient());
//            mailMessage.setSubject(email.getSubject());
//            mailMessage.setText(email.getMessage());
//            javaMailSender.send(mailMessage);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom("Cupid Dating<"+sender+">");
            helper.setTo(email.getRecipient());
            helper.setSubject(email.getSubject());
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

            HttpStatus status = HttpStatus.OK;
            SuccessDTO successDTO = new SuccessDTO(
                    "Email sent successfully!",
                    status.name(),
                    status.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(successDTO);
        } catch (Exception e) {

            HttpStatus status = HttpStatus.FORBIDDEN;
            ExceptionDTO exceptionDTO = new ExceptionDTO(
                    "Email sent error!", // message
                    status.name(), // error
                    status.value(), // statusCode
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDTO);
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDTO email) throws MessagingException {
        try {
            // Create mime message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("Cupid Dating<"+sender+">");
            mimeMessageHelper.setTo(email.getRecipient());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getMessage());

            // adding the attachment
            mimeMessageHelper.addAttachment(email.getAttachment().getOriginalFilename(),
                    email.getAttachment());

            // send the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent with attachment successfully!";
        } catch (Exception e) {
            System.out.println(e);
            return "Mail sending with attachment error!";
        }
    }
}