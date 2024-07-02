package com.ecommerce.shopping.user.repositoty;

import com.ecommerce.shopping.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
