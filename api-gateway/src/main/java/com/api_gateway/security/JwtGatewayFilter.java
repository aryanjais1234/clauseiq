package com.api_gateway.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtGatewayFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Public endpoints
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                      "error": "Unauthorized",
                      "message": "Authorization token missing"
                    }
                    """);
            return;
        }

        try {

            String token = authHeader.substring(7);

            // Validate JWT
            String userId = jwtService.getUserIdFromToken(token);

            UUID tenantId = jwtService.getTenantIdFromToken(token);

            String role = jwtService.getRoleFromToken(token);

            String username = jwtService.getUsernameFromToken(token);

            // Never trust client supplied values
            MutableHttpServletRequest wrappedRequest =
                    new MutableHttpServletRequest(request);

            wrappedRequest.putHeader(
                    "X-User-Id",
                    userId
            );

            wrappedRequest.putHeader(
                    "X-Tenant-Id",
                    tenantId.toString()
            );

            wrappedRequest.putHeader(
                    "X-Role",
                    role
            );

            wrappedRequest.putHeader(
                    "X-Username",
                    username
            );

            filterChain.doFilter(
                    wrappedRequest,
                    response
            );

        } catch (JwtException |
                 IllegalArgumentException |
                 NullPointerException e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                      "error": "Unauthorized",
                      "message": "Invalid or expired JWT"
                    }
                    """);
        }
    }

    private boolean isPublicEndpoint(String path) {

        return path.startsWith("/api/v1/auth/login")
                || path.startsWith("/api/v1/auth/register")
                || path.startsWith("/actuator/health");
    }
}