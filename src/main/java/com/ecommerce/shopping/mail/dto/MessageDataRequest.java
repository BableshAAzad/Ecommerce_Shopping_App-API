package com.ecommerce.shopping.mail.dto;

import lombok.Getter;

@Getter
public class MessageDataRequest {
    private String to;
    private String subject;
    private String text;
}
