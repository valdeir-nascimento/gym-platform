package io.github.gym.platform.api.application.workout.create;

import java.time.Instant;

public record CreateWorkoutCommand(
    String memberId,
    String academyId,
    String teacherId,
    String name,
    String objective,
    String observations,
    Instant startAt,
    Instant endAt
) {
    public static CreateWorkoutCommand with(
        final String memberId,
        final String academyId,
        final String teacherId,
        final String name,
        final String objective,
        final String observations,
        final Instant startAt,
        final Instant endAt
    ) {
        return new CreateWorkoutCommand(
            memberId,
            academyId,
            teacherId,
            name,
            objective,
            observations,
            startAt,
            endAt
        );
    }
}
