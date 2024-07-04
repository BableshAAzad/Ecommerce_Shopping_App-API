package com.ecommerce.shopping.user.repositoty;

import com.ecommerce.shopping.user.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    Optional<AccessToken> findByAccessToken(String at);

    List<AccessToken> findByIsBlockedFalse();
}
