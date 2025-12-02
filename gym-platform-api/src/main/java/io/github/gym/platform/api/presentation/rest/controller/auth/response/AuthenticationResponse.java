package io.github.gym.platform.api.presentation.rest.controller.auth.response;

import java.time.Instant;

public record AuthenticationResponse(
    String tokenType,
    String accessToken,
    Instant expiresAt
) {

    public static AuthenticationResponse bearer(final String token, final Instant expiresAt) {
        return new AuthenticationResponse("Bearer", token, expiresAt);
    }
}

