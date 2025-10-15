package com.spectrun.spectrum.services.Implementations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Nullable;
import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;

@Service
public class CallBackTokenIssuer {
    private final SecretKey callbackKey;
    private final String issuer;
    private final Duration defaultTtl;

    public CallBackTokenIssuer( @Qualifier("callbackSecretKey") SecretKey callbackKey,
         @Value("${security.callback.issuer:spectrum}") String issuer, @Value("${security.callback.ttl-hours:72}") long ttlHours) {
        this.callbackKey = callbackKey;
        this.issuer = issuer;
        this.defaultTtl = Duration.ofHours(ttlHours);
    }


    public IssuedToken issueForJob(long jobId, @Nullable Duration ttlOverride,String idemKey) {
        Duration ttl = ttlOverride != null ? ttlOverride : defaultTtl;
        Instant now = Instant.now();
        String jti = UUID.randomUUID().toString();

        String token = Jwts.builder()
                .setSubject("fastapi-manager")
                .setIssuer(issuer)
                .setAudience("fast-api-callback")
                .claim("scope", "jobs:update")
                .claim("jobId", String.valueOf(jobId))
                .claim("idemKey", idemKey)
                .setId(jti)
                .setIssuedAt(Date.from(now))
                .setNotBefore(Date.from(now))
                .setExpiration(Date.from(now.plus(ttl)))
                .signWith(SignatureAlgorithm.HS256,callbackKey)
                .compact();

        return new IssuedToken(token, jti, now.plus(ttl));
    }
    public record IssuedToken(String token, String jti, Instant expiresAt) {}

}
