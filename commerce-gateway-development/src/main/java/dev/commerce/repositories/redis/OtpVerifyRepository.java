package dev.commerce.repositories.redis;

import dev.commerce.redis.OtpVerify;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("otpVerifyRedisRepository")
public interface OtpVerifyRepository extends CrudRepository<OtpVerify, UUID> {
}
