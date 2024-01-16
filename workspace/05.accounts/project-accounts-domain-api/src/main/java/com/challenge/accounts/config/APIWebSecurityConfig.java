package com.challenge.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class APIWebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .antMatchers("/health",
                        "/actuator",
                        "/actuator/**",
                        "/actuator/info",
                        "/actuator/health",
                        "/actuator/health/**",
                        "/health",
                        "/health/**",
                        "/h2-console",
                        "/h2-console/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        return http.build();

    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    List<String> userRoleAuthorities = jwt.getClaimAsStringList("authorities");

                    if(userRoleAuthorities == null) {
                        userRoleAuthorities = Collections.emptyList();
                    }

                    JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();

                    Collection<GrantedAuthority> scopeAuthorities = scopesConverter.convert(jwt);

                    scopeAuthorities
                            .addAll(userRoleAuthorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());

                    return scopeAuthorities;
                }
        );

        return converter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
