package com.user_service.security;

import com.user_service.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JwtService {
    String generateToken(AppUser user);

    String extractUsername(String token);

    UUID extractTenantId(String token);

    String extractRole(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
