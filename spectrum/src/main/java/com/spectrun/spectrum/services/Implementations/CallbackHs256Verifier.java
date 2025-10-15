package com.spectrun.spectrum.services.Implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import  io.jsonwebtoken.JwtException;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import com.spectrun.spectrum.utils.exceptions.CallbackAuthException;

@Component
public class CallbackHs256Verifier {

    private  SecretKey secretKey;
    private  String expectedIss;
    private  String expectedAud;

    public CallbackHs256Verifier(@Qualifier("callbackSecretKey") SecretKey secretKey,
                                 @Value("${security.callback.issuer}") String expectedIss,
                                 @Value("${security.callback.audience}") String expectedAud) {
        this.secretKey = secretKey;
        this.expectedIss = expectedIss;
        this.expectedAud = expectedAud;
    }


    public Claims verifyClaims(String jwt, long expectedJobId, String expectedJti){
        try {
            Claims c = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .requireAudience(expectedAud)
                    .requireIssuer(expectedIss)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String idemKey = c.get("idemKey",String.class);
            long jobId = c.get("jobId",Long.class);
            if(expectedJobId != jobId){
                throw new CallbackAuthException(HttpStatus.FORBIDDEN.toString(), "TOKEN_SUBJECT_MISMATCH", "Token subject mismatch");
            }
            if(!Objects.equals(idemKey,expectedJti)){
                throw new CallbackAuthException(HttpStatus.CONFLICT.toString(), "IDEMPOTENCY_MISMATCH", "Token jti != idempotency key");
            }


            Date exp = c.getExpiration();
            if (exp == null || exp.before(new Date())) {
                throw new CallbackAuthException(HttpStatus.FORBIDDEN.toString(), "TOKEN_EXPIRED", "Callback token expired");
            }
            return c;
        }catch (JwtException e){
            throw new CallbackAuthException(HttpStatus.FORBIDDEN.toString(), "TOKEN_INVALID", "Invalid callback token");
        }
    }

}
