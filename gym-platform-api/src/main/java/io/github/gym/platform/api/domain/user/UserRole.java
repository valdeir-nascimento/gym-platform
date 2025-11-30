package io.github.gym.platform.api.domain.user;

public enum UserRole {
    ADMIN,
    MEMBER,
    TEACHER;

    public static UserRole from(final String value) {
        return UserRole.valueOf(value.toUpperCase());
    }
}

