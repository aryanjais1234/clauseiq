package com.api_gateway.config;

import com.api_gateway.security.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

public class TenantHeaderFilter {

    private TenantHeaderFilter() {
    }

    public static Function<ServerRequest, ServerRequest> addTenantHeader(
            JwtService jwtService) {

        return request -> {

            String authHeader =
                    request.headers().firstHeader("Authorization");

            if (authHeader == null ||
                    !authHeader.startsWith("Bearer ")) {

                throw new JwtException("Authorization token missing");
            }

            String token = authHeader.substring(7);

            // JWT signature + expiration are validated here
            String tenantId =
                    jwtService.getTenantIdFromToken(token).toString();

            String userId =
                    jwtService.getUserIdFromToken(token);

            // Remove client supplied headers first
            // and add trusted values from JWT.
            return ServerRequest
                    .from(request)
                    .headers(headers -> {
                        headers.remove("X-Tenant-Id");
                        headers.remove("X-User-Id");
                    })
                    .header("X-Tenant-Id", tenantId)
                    .header("X-User-Id", userId)
                    .build();
        };
    }
}