package io.github.gym.platform.api.domain.plan;

import io.github.gym.platform.api.domain.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class PlanID extends Identifier<UUID> {

    private final UUID value;

    private PlanID(final UUID value) {
        this.value = value;
    }

    public static PlanID unique() {
        return new PlanID(UUID.randomUUID());
    }

    public static PlanID from(final UUID value) {
        return new PlanID(Objects.requireNonNull(value));
    }

    public static PlanID from(final String value) {
        return new PlanID(UUID.fromString(value));
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
