package com.challenge.authentication.domain;

public record RegisteredClient(
    String      id,
    String      clientId,
    String      clientIdIssuedAt,
    String      clientSecret,
    String      clientSecretExpiresAt,
    String      clientName,
    String      clientAuthenticationMethods,
    String      authorizationGrantTypes,
    String      redirectUris,
    String      scopes,
    String      clientSettings,
    String      tokenSettings
) {
}
