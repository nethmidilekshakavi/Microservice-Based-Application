package com.example.userservice.config;

import com.example.userservice.service.JWTService;
import com.example.userservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println(("JWT Filter processing request for: " + request.getRequestURI()));

        // Skip filter if no Authorization header
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUserName(jwt);

            if (StringUtils.isEmpty(userEmail)) {
                System.out.println("JWT token contains no user identifier");
                filterChain.doFilter(request, response);
                return;
            }

            // Skip if already authenticated
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService()
                        .loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    authenticateUser(request, userDetails);
                    System.out.println("Authenticated user: " + userEmail);
                } else {
                    System.out.println("Invalid JWT token for user: " + userEmail);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("JWT Authentication failed", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // Consider sending an error response here if needed
        }
    }

    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}
