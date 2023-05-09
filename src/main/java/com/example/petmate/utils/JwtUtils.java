package com.example.petmate.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.exception.ResponseException;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Slf4j
public class JwtUtils {

	@Value("${jwt.secret-key}")
	private static String secretKey = "my-secret";

	public static String createJwt(String userId, String userRole) {
		log.info(secretKey);
		Date now = new Date();
		long expiresInMinutes = 300;
		Date expiration = new Date(now.getTime() + expiresInMinutes * 60000);
		Algorithm algorithm =  Algorithm.HMAC256(secretKey);
		return JWT.create()
				.withClaim("userId", userId)
				.withClaim("userRole", userRole)
				.withExpiresAt(expiration)
				.sign(algorithm);
	}

	public static String getUserIdFromToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
			return decodedJWT.getClaim("userId").asString();
		} catch (JWTVerificationException e) {
			return null;
		}
	}

	public static JsonObject checkRequestJwt(HttpServletRequest request) throws ResponseException {
		JsonObject json = verifyToken(request);
		if (json==null) throw new ResponseException(ResponseCodes.RT_ERROR_SESSION_TIMEOUT);
		return json;
	}

	private static JsonObject verifyToken(HttpServletRequest request) throws ResponseException {
		String header = request.getHeader("Authorization");
		if (header==null || !header.substring(0, 7).equals("Bearer ")) throw new ResponseException(ResponseCodes.RT_ERROR_VERIFY_FAIL);
		String jwtToken = header.substring(7);
		return verifyToken(jwtToken);
	}

	private static JsonObject verifyToken(String jwtToken) throws ResponseException {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(jwtToken);
			String jwtId = jwt.getHeaderClaim("userId").asString();
			JsonObject json = new JsonObject();
			json.addProperty("userId", jwtId);
			return json;
		} catch (Exception e) {
			throw new ResponseException(ResponseCodes.RT_ERROR_VERIFY_FAIL, e.toString());
		}
	}
}
