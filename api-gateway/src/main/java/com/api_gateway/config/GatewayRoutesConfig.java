package com.api_gateway.config;

import com.api_gateway.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class GatewayRoutesConfig {

    private final JwtService jwtService;

    @Bean
    public RouterFunction<ServerResponse> ingestionRoute() {

        return route("ingestion-service")
                .POST("/api/v1/ingestion/**", http())

                // Eureka / Load Balancer target
                .filter(lb("ingestion-service"))

                // JWT -> tenantId/userId -> downstream headers
                .before(TenantHeaderFilter.addTenantHeader(jwtService))

                .build();
    }
}