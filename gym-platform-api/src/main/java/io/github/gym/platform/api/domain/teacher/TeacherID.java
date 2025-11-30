package io.github.gym.platform.api.domain.teacher;

import io.github.gym.platform.api.domain.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class TeacherID extends Identifier<UUID> {

    private final UUID value;

    private TeacherID(final UUID value) {
        this.value = value;
    }

    public static TeacherID unique() {
        return new TeacherID(UUID.randomUUID());
    }

    public static TeacherID from(final UUID value) {
        return new TeacherID(Objects.requireNonNull(value));
    }

    public static TeacherID from(final String value) {
        return new TeacherID(UUID.fromString(value));
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
