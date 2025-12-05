package io.github.gym.platform.api.domain.workout;

import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.ValidationHandler;
import io.github.gym.platform.api.domain.validation.Validator;

import java.time.Instant;

public class WorkoutValidator extends Validator {

    private final Workout workout;

    public WorkoutValidator(final Workout workout, final ValidationHandler handler) {
        super(handler);
        this.workout = workout;
    }

    @Override
    public void validate() {
        checkMemberId();
        checkAcademyId();
        checkTeacherId();
        checkName();
        checkObjective();
        checkActive();
        checkSchedule();
        checkTimestamps();
    }

    private void checkMemberId() {
        if (workout.getMemberId() == null) {
            this.validationHandler().append(Error.of("'memberId' should not be null"));
        }
    }

    private void checkAcademyId() {
        if (workout.getAcademyId() == null) {
            this.validationHandler().append(Error.of("'academyId' should not be null"));
        }
    }

    private void checkTeacherId() {
        if (workout.getTeacherId() == null) {
            this.validationHandler().append(Error.of("'teacherId' should not be null"));
        }
    }

    private void checkName() {
        final var name = workout.getName();

        if (name == null || name.isBlank()) {
            this.validationHandler().append(Error.of("'name' should not be null or blank"));
            return;
        }

        final var trimmed = name.trim();
        if (trimmed.length() < 3) {
            this.validationHandler().append(Error.of("'name' must have at least 3 characters"));
        }

        if (trimmed.length() > 255) {
            this.validationHandler().append(Error.of("'name' must have at most 255 characters"));
        }
    }

    private void checkObjective() {
        final var objective = workout.getObjective();

        if (objective == null || objective.isBlank()) {
            this.validationHandler().append(Error.of("'objective' should not be null or blank"));
        }
    }

    private void checkActive() {
        if (workout.isActive() == null) {
            this.validationHandler().append(Error.of("'active' should not be null"));
        }
    }

    private void checkSchedule() {
        final Instant start = workout.getStartAt();
        final Instant end = workout.getEndAt();

        if (start == null) {
            this.validationHandler().append(Error.of("'startAt' should not be null"));
            return;
        }

        if (end != null && end.isBefore(start)) {
            this.validationHandler().append(
                Error.of("'endAt' should be after or equal to 'startAt'")
            );
        }
    }

    private void checkTimestamps() {
        if (workout.getCreatedAt() == null) {
            this.validationHandler().append(Error.of("'createdAt' should not be null"));
        }
        if (workout.getUpdatedAt() == null) {
            this.validationHandler().append(Error.of("'updatedAt' should not be null"));
        }
    }
}
