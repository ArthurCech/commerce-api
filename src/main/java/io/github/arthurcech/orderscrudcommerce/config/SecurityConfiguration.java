package io.github.arthurcech.orderscrudcommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final String CATEGORIES = "/api/categories/**";
    private final String PRODUCTS = "/api/products/**";
    private final String CLIENTS = "/api/clients/**";
    private final String USERS = "/api/users/**";
    private final String ORDERS = "/api/orders/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/authenticate").permitAll();
                    auth.requestMatchers(toH2Console()).permitAll();
                    auth.requestMatchers("/api/auth/register").hasRole("ADMIN");
                    auth.requestMatchers("/api/auth/reset-password").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers("/api/users/profile").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers(USERS).hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, new String[]{CATEGORIES, ORDERS}).hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, new String[]{CATEGORIES, ORDERS}).hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, new String[]{CATEGORIES, ORDERS}).hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, new String[]{ORDERS}).hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .headers(headers -> headers.frameOptions().sameOrigin())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
