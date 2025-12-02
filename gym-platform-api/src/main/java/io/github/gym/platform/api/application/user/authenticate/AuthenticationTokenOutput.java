package io.github.gym.platform.api.application.user.authenticate;

import java.time.Instant;

public record AuthenticationTokenOutput(
    String accessToken,
    Instant expiresAt
) {
}

