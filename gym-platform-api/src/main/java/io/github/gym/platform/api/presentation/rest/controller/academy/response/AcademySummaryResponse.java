package io.github.gym.platform.api.presentation.rest.controller.academy.response;

import io.github.gym.platform.api.application.academy.AcademyOutput;

public record AcademySummaryResponse(
    String id,
    String name,
    boolean active,
    String address
) {

    public static AcademySummaryResponse from(final AcademyOutput output) {
        return new AcademySummaryResponse(
            output.id(),
            output.name(),
            output.active(),
            output.address()
        );
    }
}

