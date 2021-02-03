package com.mindweaver.security;


import static com.mindweaver.security.SecurityConstants.jwtExpirationinMs;
import static com.mindweaver.security.SecurityConstants.jwtSecretKey;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
	private String jwtSecret=jwtSecretKey;
	
	private long jwtExpirationInMs=jwtExpirationinMs;
	

	// 
    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        // set the expiry time for the jwt token (expiry time is 10 day)
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        // customerId is phone number
        String customerId = userPrincipal.getPhone() ;

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(customerId));
        claims.put("email",userPrincipal.getEmail() );
        claims.put("password", userPrincipal.getPassword());
        
        return Jwts.builder()
        		.setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
   
    

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
        	System.out.println("Invalid JWT signature");
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}