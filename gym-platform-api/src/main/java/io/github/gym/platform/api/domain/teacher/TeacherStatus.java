package io.github.gym.platform.api.domain.teacher;

public enum TeacherStatus {
    ACTIVE,
    INACTIVE;

    public static TeacherStatus from(final String value) {
        return TeacherStatus.valueOf(value.toUpperCase());
    }
}
