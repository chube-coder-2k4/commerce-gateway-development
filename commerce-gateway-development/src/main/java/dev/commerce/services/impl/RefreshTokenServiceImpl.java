package dev.commerce.services.impl;

import dev.commerce.redis.RefreshToken;
import dev.commerce.repositories.redis.RefreshTokenRedisRepository;
import dev.commerce.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRedisRepository refreshTokenRepository;
    @Override
    public void deleteByUserId(UUID userId) {
        refreshTokenRepository.deleteByUsersId(userId);
    }

    @Override
    public String saveRefreshToken(RefreshToken refreshToken) {
        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
        return savedToken.getToken();
    }

    @Override
    public RefreshToken getByUserId(UUID userId) {
        return refreshTokenRepository.findByUsersId(userId);
    }
}
