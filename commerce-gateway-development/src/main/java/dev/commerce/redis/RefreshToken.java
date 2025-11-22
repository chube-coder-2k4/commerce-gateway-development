package dev.commerce.redis;

import dev.commerce.entitys.BaseEntity;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "RefreshToken", timeToLive = 604800)
public class RefreshToken extends BaseEntity {
    @Id
    private String token;
    @Column(name = "user_id")
    private UUID usersId;
    private boolean revoked = false;
}
