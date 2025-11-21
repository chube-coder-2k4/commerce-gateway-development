package dev.commerce.services;

import dev.commerce.entitys.Users;
import dev.commerce.redis.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    void deleteByUserId(UUID userId);
    String saveRefreshToken(RefreshToken refreshToken);
    RefreshToken getByUserId(UUID userId);
}
