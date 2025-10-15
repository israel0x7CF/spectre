package com.spectrun.spectrum.config.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.HexFormat;

@Configuration
public class CallbackSecretConfig {
    @Value("${security.callback.shared-secret}")
    private String callbackSecret;

    @Bean("callbackSecretKey")
    public SecretKey callbackSecretKey(){
        byte[] keyBytes;


            if (callbackSecret.matches("^[0-9a-fA-F]+$") && (callbackSecret.length() % 2 == 0)){
                keyBytes = HexFormat.of().parseHex(callbackSecret);
            }
            else {
                keyBytes = Decoders.BASE64.decode(callbackSecret);
            }

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("HS256 key must be at least 256 bits (32 bytes) after decoding.");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
