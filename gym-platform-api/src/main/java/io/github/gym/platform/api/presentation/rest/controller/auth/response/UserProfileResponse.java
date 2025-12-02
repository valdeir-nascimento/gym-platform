package io.github.gym.platform.api.presentation.rest.controller.auth.response;

import io.github.gym.platform.api.infrastructure.security.JwtAuthenticatedUser;

import java.util.Set;
import java.util.stream.Collectors;

public record UserProfileResponse(
    String id,
    String fullName,
    String email,
    Set<String> roles
) {

    public static UserProfileResponse from(final JwtAuthenticatedUser user) {
        return new UserProfileResponse(
            user.id().toString(),
            user.fullName(),
            user.email(),
            user.roles().stream().map(Enum::name).collect(Collectors.toUnmodifiableSet())
        );
    }
}

