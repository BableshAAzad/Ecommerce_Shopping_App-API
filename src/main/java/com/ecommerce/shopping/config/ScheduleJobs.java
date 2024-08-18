package com.ecommerce.shopping.config;

import com.ecommerce.shopping.user.repositoty.AccessTokenRepository;
import com.ecommerce.shopping.user.repositoty.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class ScheduleJobs {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

//    18000000 = 5hours
    @Scheduled(fixedDelay = 18000000L) // after 5 minutes again and again start
    public void cleanExpiredAccessToken() {
        accessTokenRepository.findByExpirationBefore(LocalDateTime.now())
                .forEach(accessTokenRepository::delete);
    }

    @Scheduled(fixedDelay = 18000000L) // after 5 minutes again and again start
    public void cleanExpiredRefreshToken() {
        refreshTokenRepository.findByExpirationBefore(LocalDateTime.now())
                .forEach(refreshTokenRepository::delete);
    }
}
