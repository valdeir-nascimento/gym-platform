package io.github.gym.platform.api.presentation.rest.controller.auth.response;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.infrastructure.security.JwtAuthenticatedUser;
import io.github.gym.platform.api.presentation.rest.controller.academy.response.AcademySummaryResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record UserProfileResponse(
    String id,
    String fullName,
    String email,
    Set<String> roles,
    List<AcademySummaryResponse> academies,
    String defaultAcademyId
) {

    public static UserProfileResponse from(
        final JwtAuthenticatedUser user,
        final List<AcademyOutput> academies
    ) {
        final var academyResponses = academies.stream()
            .map(AcademySummaryResponse::from)
            .toList();

        final var defaultAcademyId = academyResponses.stream()
            .filter(AcademySummaryResponse::active)
            .findFirst()
            .or(() -> academyResponses.stream().findFirst())
            .map(AcademySummaryResponse::id)
            .orElse(null);

        return new UserProfileResponse(
            user.id().toString(),
            user.fullName(),
            user.email(),
            user.roles().stream().map(Enum::name).collect(Collectors.toUnmodifiableSet()),
            academyResponses,
            defaultAcademyId
        );
    }
}

