package com.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserIdFromToken(String token) {

        return extractAllClaims(token)
                .get("userId", String.class);
    }

    public UUID getTenantIdFromToken(String token) {

        String tenantId = extractAllClaims(token)
                .get("tenantId", String.class);

        return UUID.fromString(tenantId);
    }

    public String getRoleFromToken(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    public String getUsernameFromToken(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}