package com.spectrun.spectrum;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import io.jsonwebtoken.security.Keys;

import java.security.SecureRandom;
import java.util.Base64;
import java.security.Key;

@SpringBootApplication
@EnableScheduling
public class SpectrumApplication {

	public static void main(String[] args) {

			SecureRandom secureRandom = new SecureRandom();
			byte[] secretBytes = new byte[36]; //36*8=288 (>256 bits required for HS256)
			secureRandom.nextBytes(secretBytes);
			Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
			System.out.println(encoder);


		SpringApplication.run(SpectrumApplication.class, args);
	}

}
