package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtils {
	
	private static final Logger LOG=LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${app.jwt.secret}")
	private String jwtSecret;


	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
			return true;

		}catch(ExpiredJwtException exception) {
			LOG.error("Token expirado {}", exception.getMessage());
		}
		catch(SignatureException exception) {
			LOG.error("Token invalido {}",exception.getMessage());
		}
		return false;
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
				.getBody().getSubject();
	}
}
