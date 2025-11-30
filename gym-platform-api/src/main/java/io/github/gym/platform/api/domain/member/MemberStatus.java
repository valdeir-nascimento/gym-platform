package io.github.gym.platform.api.domain.member;

public enum MemberStatus {
    ACTIVE,
    DELINQUENT,
    CANCELED;

    public static MemberStatus from(final String value) {
        return MemberStatus.valueOf(value.toUpperCase());
    }
}
