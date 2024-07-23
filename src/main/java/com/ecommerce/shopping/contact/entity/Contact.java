package com.ecommerce.shopping.contact.entity;

import com.ecommerce.shopping.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;
    private Long contactNumber=0L;
    @Enumerated(EnumType.STRING)
    private Priority priority;
}
