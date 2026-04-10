package com.example.distribution_sales_techfira.service.impl;

import com.example.distribution_sales_techfira.service.ForgotEmailService;
import com.example.distribution_sales_techfira.service.ForgotUserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ForgotEmailServiceImp implements ForgotEmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public ForgotEmailServiceImp(JavaMailSender  mailSender) {
        this.mailSender = mailSender;
    }
    public void sendSimpleResetEmail(String to, String token) throws MessagingException {
       // String resetLink = "http://localhost:8080/reset-password?token=" + token;

        String subject = "Reset Your Password";
        String body = "Click the link below to reset your password:\n\n" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        mailSender.send(message);
    }


//    public void sendHtmlEmailWithImage(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException, IOException {
//        Context context = new Context();
//        context.setVariables(variables);
//
//        String htmlContent = templateEngine.process(templateName, context);
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlContent, true);
//
//        // Add inline image from classpath
//        ClassPathResource image = new ClassPathResource("techfira-logo.jpg");
//        helper.addInline("techfiraLogo", image);
//
//        mailSender.send(message); // ✅ Send the constructed message
//    }
}
