package com.ticketapp.apigateway.authFilter;

import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.ticketapp.apigateway.DTO.TokenIntrospectRequest;
import com.ticketapp.apigateway.DTO.TokenIntrospectResponse;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    // GlobalFilter runs for every request passing throught the gateway
    // Ordered enables execution priority to set certain methods to be executed first

    private static final List<String> PUBLIC_PATHS = List.of(
        "/auth-service/login",
        "/auth-service/introspect"
    );

    private final WebClient webClient;

    public AuthenticationFilter(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        
        String path = exchange.getRequest().getURI().getPath();
        
        if(isPublicPath(path)){
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing Authorization header");
        }

        String token = authHeader.substring(7);

        TokenIntrospectRequest request = new TokenIntrospectRequest();
        request.setToken(token);

        return webClient.post()
                .uri("http://localhost:8080/auth-service/introspect")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TokenIntrospectResponse.class)
                .flatMap(response -> {
                    if (!response.isActive()) {
                        return unauthorized(exchange, "Invalid token");
                    }
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-User-Id", response.getUsername())
                            .build();
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build();
                    return chain.filter(mutatedExchange);
                });
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder(){
        return -1;
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
