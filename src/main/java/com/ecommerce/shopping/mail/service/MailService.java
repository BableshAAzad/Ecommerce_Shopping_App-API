package com.ecommerce.shopping.mail.service;

import com.ecommerce.shopping.mail.dto.MessageData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(MessageData messageData) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(messageData.getTo());
        helper.setSubject(messageData.getSubject());
        helper.setSentDate(messageData.getSendDate());
        helper.setText(messageData.getText(), true);
        javaMailSender.send(message);
    }
}
