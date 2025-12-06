package io.github.gym.platform.api.presentation.rest.controller.workout.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateWorkoutRequest(
    @NotBlank(message = "'memberId' must not be blank")
    String memberId,

    @NotBlank(message = "'academyId' must not be blank")
    String academyId,

    @NotBlank(message = "'teacherId' must not be blank")
    String teacherId,

    @NotBlank(message = "'name' must not be blank")
    String name,

    @NotBlank(message = "'objective' must not be blank")
    String objective,

    String observations,

    @NotNull(message = "'startAt' must not be null")
    Instant startAt,

    Instant endAt
) {
}

