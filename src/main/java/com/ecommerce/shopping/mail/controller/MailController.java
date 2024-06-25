package com.ecommerce.shopping.mail.controller;

import com.ecommerce.shopping.mail.dto.MessageDataRequest;
import com.ecommerce.shopping.mail.entity.MessageData;
import com.ecommerce.shopping.mail.mapper.MailMapper;
import com.ecommerce.shopping.mail.service.MailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MailController {

//    private final MailMapper mapper;
//
//    private MailService mailService;
//
//    @PostMapping("/mailDemo")
//    private String mailSendDemo(@RequestBody MessageDataRequest messageDataRequest) throws MessagingException {
//        mailService.sendMail(mapper.mapMassageDataRequestToMessageData(messageDataRequest, new MessageData()));
//        return "Done";
//    }
}
