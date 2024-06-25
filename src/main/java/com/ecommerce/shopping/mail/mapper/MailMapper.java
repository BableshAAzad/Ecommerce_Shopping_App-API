package com.ecommerce.shopping.mail.mapper;

import com.ecommerce.shopping.mail.dto.MessageDataRequest;
import com.ecommerce.shopping.mail.entity.MessageData;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MailMapper {

    public MessageData mapMassageDataRequestToMessageData(
            MessageDataRequest messageDataRequest, MessageData messageData) {
        messageData.setTo(messageDataRequest.getTo());
        messageData.setSubject(messageDataRequest.getSubject());
        messageData.setText(messageDataRequest.getText());
        messageData.setSendDate(new Date());
        return messageData;

    }
}
