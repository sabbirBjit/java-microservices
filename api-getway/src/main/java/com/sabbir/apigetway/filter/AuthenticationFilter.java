package com.sabbir.apigetway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authToken = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authToken == null || !isValidToken(authToken)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    private boolean isValidToken(String token) {
        // Implement token validation logic here (e.g., JWT validation)
        return true; // Placeholder for actual validation
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }
}
