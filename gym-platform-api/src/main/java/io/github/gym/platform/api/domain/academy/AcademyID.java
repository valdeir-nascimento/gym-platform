package io.github.gym.platform.api.domain.academy;

import io.github.gym.platform.api.domain.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class AcademyID extends Identifier<UUID> {

    private final UUID value;

    private AcademyID(final UUID value) {
        this.value = value;
    }

    public static AcademyID unique() {
        return new AcademyID(UUID.randomUUID());
    }

    public static AcademyID from(final UUID value) {
        return new AcademyID(Objects.requireNonNull(value));
    }

    public static AcademyID from(final String value) {
        return new AcademyID(UUID.fromString(value));
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
