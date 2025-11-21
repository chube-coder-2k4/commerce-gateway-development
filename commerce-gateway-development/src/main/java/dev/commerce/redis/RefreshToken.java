package dev.commerce.redis;

import dev.commerce.entitys.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "RefreshToken", timeToLive = 604800)
public class RefreshToken extends BaseEntity {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID usersId;
    private String token;
    private boolean revoked = false;
    public void ensureId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
