package io.github.gym.platform.api.domain.workout;

import io.github.gym.platform.api.domain.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class WorkoutID extends Identifier<UUID> {

    private final UUID value;

    private WorkoutID(final UUID value) {
        this.value = value;
    }

    public static WorkoutID unique() {
        return new WorkoutID(UUID.randomUUID());
    }

    public static WorkoutID from(final UUID value) {
        return new WorkoutID(Objects.requireNonNull(value));
    }

    public static WorkoutID from(final String value) {
        return new WorkoutID(UUID.fromString(value));
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
