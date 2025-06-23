package com.sabbir.apigetway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("inventory_service", r -> r.path("/api/inventory/**")
                        .uri("http://localhost:8082"))
                .route("order_service", r -> r.path("/api/orders/**")
                        .uri("http://localhost:8081"))
                .route("product_service", r -> r.path("/api/products/**")
                        .uri("http://localhost:8080"))
                .build();
    }
}
