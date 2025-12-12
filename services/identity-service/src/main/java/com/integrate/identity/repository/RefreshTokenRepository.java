package com.integrate.identity.repository;

import com.integrate.identity.domain.RefreshToken;
import com.integrate.identity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String refreshToken);

    @Modifying
    int deleteByUser(User user);
}
