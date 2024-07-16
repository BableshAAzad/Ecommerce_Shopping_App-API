package com.ecommerce.shopping.user.repositoty;

import com.ecommerce.shopping.user.entity.AccessToken;
import com.ecommerce.shopping.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    Optional<AccessToken> findByAccessToken(String at);

    List<AccessToken> findByUserAndIsBlockedAndAccessTokenNot(User user, boolean b, String accessToken);

    List<AccessToken> findByUserAndIsBlocked(User user, boolean b);

    List<AccessToken> findByExpirationBefore(LocalDateTime now);
}
