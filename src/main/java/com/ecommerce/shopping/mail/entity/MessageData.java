package com.ecommerce.shopping.mail.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageData {
    private String to;
    private String subject;
    private String text;
    private Date sendDate;
}
