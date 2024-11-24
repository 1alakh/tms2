package com.tsm_authentication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    /**
     * for jwt secret key which is inside application.properties
     */

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-duration-ms:36000000}")
    private Long expirationDurationInMillis;

    /**
     * Extracts username from JWT token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Extracts expiration date from JWT token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from JWT token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses all claims from the JWT token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the JWT token is expired
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a new JWT token with the specified username
     */
    public String generateToken(String userId, String username, ArrayList<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, username, roles);
    }

    /**
     * Creates a JWT token with the specified claims and subject (username)
     */

    private String createToken(Map<String, Object> claims, String userId, String username, ArrayList<String> roles) {
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("roles", roles);
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationDurationInMillis)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Validates a JWT token by checking the username and expiration
     */
    public Boolean validateToken(String token , String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
