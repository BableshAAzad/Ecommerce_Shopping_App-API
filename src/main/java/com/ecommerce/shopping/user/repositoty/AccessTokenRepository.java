package com.ecommerce.shopping.user.repositoty;

import com.ecommerce.shopping.user.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}
