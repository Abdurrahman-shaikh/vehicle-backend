package com.tcs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures Spring Security for the application including:
 * - Route-based authorization
 * - Stateless JWT authentication
 * - Custom user details service and password encoding
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Defines the security filter chain for handling HTTP security.
     * It disables CSRF, configures public and protected routes,
     * enables stateless session, and adds the JWT filter.
     *
     * @param http HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception in case of any configuration error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // Public endpoints
                        .requestMatchers(
                                "/api/admin/login", "/api/register",
                                "/api/underwriter/login",
                                "/login", "/register",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/underwriters"
                        ).permitAll()

                        // Admin-protected endpoints
                        .requestMatchers("/api/underwriters/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")

                        // Underwriter-protected endpoints
                        .requestMatchers("/api/vehicle/register").hasRole("UNDERWRITER")

                        // Any other endpoint requires authentication
                        .anyRequest().authenticated()
                )
                // Stateless session (no HTTP session will be created or used)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Register JWT filter before Spring's built-in auth filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * Configures the authentication provider with a custom UserDetailsService
     * and BCrypt password encoder.
     *
     * @return configured AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    /**
     * Provides the AuthenticationManager used by Spring Security.
     *
     * @param config AuthenticationConfiguration to retrieve manager from
     * @return configured AuthenticationManager
     * @throws Exception in case of failure
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
