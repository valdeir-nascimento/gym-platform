package io.github.gym.platform.api.domain.member;

import io.github.gym.platform.api.domain.core.Identifier;

import java.util.Objects;
import java.util.UUID;

public class MemberID extends Identifier<UUID> {

    private final UUID value;

    private MemberID(final UUID value) {
        this.value = value;
    }

    public static MemberID unique() {
        return new MemberID(UUID.randomUUID());
    }

    public static MemberID from(final UUID value) {
        return new MemberID(Objects.requireNonNull(value));
    }

    public static MemberID from(final String value) {
        return new MemberID(UUID.fromString(value));
    }

    @Override
    public UUID getValue() {
        return value;
    }
}
