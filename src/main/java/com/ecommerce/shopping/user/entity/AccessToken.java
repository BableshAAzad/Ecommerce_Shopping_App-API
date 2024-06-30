package com.ecommerce.shopping.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessTokenId;
    private String accessToken;
    private LocalDateTime expiration;

    @ManyToOne
    private User user;
}
