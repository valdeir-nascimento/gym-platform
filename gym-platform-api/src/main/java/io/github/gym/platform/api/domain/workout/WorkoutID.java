package io.github.gym.platform.api.domain.workout;

import io.github.gym.platform.api.domain.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class WorkoutID extends Identifier {

    private final String value;

    private WorkoutID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static WorkoutID unique() {
        return new WorkoutID(UUID.randomUUID().toString());
    }

    public static WorkoutID from(final String value) {
        return new WorkoutID(value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
