package com.example.petmate.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtils {
	public static String createJwt(String userId, String userRole) {
		String secret = "my-secret";
		Date now = new Date();
		long expiresInMinutes = 300;
		Date expiration = new Date(now.getTime() + expiresInMinutes * 60000);
		Algorithm algorithm =  Algorithm.HMAC256(secret);
		return JWT.create()
				.withClaim("userId", userId)
				.withClaim("userRole", userRole)
				.withExpiresAt(expiration)
				.sign(algorithm);
	}
}
