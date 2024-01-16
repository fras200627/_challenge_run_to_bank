package com.challenge.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class RegisteredClientConfig {
    
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        
        List<RegisteredClient> registrations = new ArrayList<RegisteredClient>();
        
        RegisteredClient client1 = RegisteredClient
                .withId("99999")
                .clientId("admin-master")
                .clientName("Client Admin Master")
                .clientIdIssuedAt(Instant.now())
                .clientSecretExpiresAt(Instant.now().plus(3650, ChronoUnit.DAYS))
                .clientSecret(passwordEncoder.encode("123456"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client.read")
                .scope("client.write")
                .scope("client.root")
                .redirectUri("")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)  //Importante
                        .build())
                .build();

        registrations.add(client1);
        
        RegisteredClient client2 = RegisteredClient
                .withId("1")
                .clientId("client.read")
                .clientName("Client type API consumer scopes client.read")
                .clientSecretExpiresAt(Instant.now())
                .clientSecret(passwordEncoder.encode("123456"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client.read")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        registrations.add(client2);
        
        
        return new InMemoryRegisteredClientRepository(registrations);
    }
    
}
