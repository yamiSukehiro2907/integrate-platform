package com.integrate.identity.services;

import com.integrate.identity.domain.RefreshToken;
import com.integrate.identity.domain.User;
import com.integrate.identity.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void createRefreshToken(String token, User user) {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .user(user)
                .token(token)
                .expiryDate(Instant.now().plusMillis(604800000))
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    public void deleteToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }
}
