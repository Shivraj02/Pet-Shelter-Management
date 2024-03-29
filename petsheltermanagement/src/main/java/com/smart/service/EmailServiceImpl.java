package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.senderMail}")
    private String senderEmail;
    @Value("${spring.mail.senderMailName}")
    private String senderEmailName;
    
    

    @Async
    @Override
    public void sendEmail(String to, String subject,String body){

        

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail, senderEmailName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);
            mailSender.send(message);
        }
        catch (Exception e)
        {
//            throw new EmailException("Failed to send email:" + e.getMessage());
        }

    }
}
