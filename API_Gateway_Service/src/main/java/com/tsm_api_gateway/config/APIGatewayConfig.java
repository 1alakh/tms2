package com.tsm_api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
public class APIGatewayConfig {
    private static final String API = "/api/v1";
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tsm-authentication", r -> r.path(API + "/auth/**")
                        .uri("http://localhost:8000"))
                .route("tsm-user", r -> r.path(API+ "/users/**")
                        .uri("http://localhost:9002"))
                .route("tsm-user", r -> r.path(API+ "/roles/**")
                        .uri("http://localhost:9002"))
                .route("tsm-authentication", r -> r.path(API + "/projects/**")
                        .uri("http://localhost:9003"))
                .route("tsm-authentication", r -> r.path(API + "/tasks/**")
                        .uri("http://localhost:9003"))
                .route("tsm-timesheet", r -> r.path(API + "/timeentries/**")
                        .uri("http://localhost:9004"))

                .build();
    }
}
