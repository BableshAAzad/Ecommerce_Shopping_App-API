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
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;
    private String refreshToken;
    private LocalDateTime expiration;

    @ManyToOne
    private User user;
}
