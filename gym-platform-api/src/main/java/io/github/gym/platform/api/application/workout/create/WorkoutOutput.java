package io.github.gym.platform.api.application.workout.create;

import io.github.gym.platform.api.domain.workout.Workout;

import java.time.Instant;

public record WorkoutOutput(
    String id,
    String memberId,
    String academyId,
    String teacherId,
    String name,
    String objective,
    String observations,
    String status,
    Boolean active,
    Instant startAt,
    Instant endAt,
    Instant createdAt,
    Instant updatedAt
) {

    public static WorkoutOutput from(final Workout workout) {
        return new WorkoutOutput(
            workout.getId().getValue(),
            workout.getMemberId().getValue().toString(),
            workout.getAcademyId().getValue().toString(),
            workout.getTeacherId().getValue().toString(),
            workout.getName(),
            workout.getObjective(),
            workout.getObservations(),
            workout.getStatus().name(),
            workout.isActive(),
            workout.getStartAt(),
            workout.getEndAt(),
            workout.getCreatedAt(),
            workout.getUpdatedAt()
        );
    }
}
