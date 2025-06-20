package com.example.apigateway;

import com.example.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

//    @Value("${jwt.secret}")
//    private String secret;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        System.out.printf("hello");
//        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return chain.filter(exchange);
//        }
//
//        String token = authHeader.substring(7);
//        try {
//            String email = jwtUtil.extractUsername(token);
//            String role = jwtUtil.extractRole(token);
//            System.out.println("role: " + role);
//
//            if (email == null || jwtUtil.isTokenExpired(token)) {
//                return chain.filter(exchange);
//            }
//
//            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
//            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
//
//            SecurityContext context = new SecurityContextImpl(authentication);
//            return chain.filter(exchange)
//                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
//
//        } catch (Exception e) {
//            return chain.filter(exchange);
//}
//}

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.printf("hello");
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);
        try {
            String email = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            System.out.println("role: " + role);

            if (email == null || jwtUtil.isTokenExpired(token)) {
                return chain.filter(exchange);
            }

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContext context = new SecurityContextImpl(authentication);
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));

        } catch (Exception e) {
            return chain.filter(exchange);
        }
    }
}