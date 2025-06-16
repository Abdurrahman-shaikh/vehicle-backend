package com.tcs.config;

import com.tcs.entity.Login;
import com.tcs.repository.LoginRepository;
import com.tcs.service.CustomUserDetailsService;
import com.tcs.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT filter that intercepts all incoming requests to validate the JWT token.
 * It skips authentication for specific public endpoints like login and Swagger UI.
 * For authenticated requests, it verifies the JWT and sets authentication in the security context.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Filters every request once and validates JWT if applicable.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException in case of errors during filtering
     * @throws IOException      in case of I/O errors
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip JWT validation for public endpoints
        if (
                path.equals("/api/admin/login") ||
                        path.equals("/api/login") ||
                        path.equals("/login") ||
                        path.startsWith("/swagger-ui") ||
                        path.startsWith("/v3/api-docs") ||
                        path.startsWith("/h2-console/**")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // Parse JWT token from Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Strip "Bearer " prefix
        username = jwtService.extractUsername(jwt); // Extract username from token

        // If user is not already authenticated and token is valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Set user authentication in security context
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the next filter
        filterChain.doFilter(request, response);
    }

    /**
     * Determines whether this filter should not be applied to the given request.
     * Used for Swagger and API documentation endpoints.
     *
     * @param request the HTTP servlet request
     * @return true if the filter should not apply
     * @throws ServletException in case of errors
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.equals("/swagger-ui.html");
    }
}
