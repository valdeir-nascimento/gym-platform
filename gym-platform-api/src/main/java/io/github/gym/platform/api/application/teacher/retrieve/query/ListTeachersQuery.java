package io.github.gym.platform.api.application.teacher.retrieve.query;

public record ListTeachersQuery(String academyId) {

    public static ListTeachersQuery with(final String academyId) {
        return new ListTeachersQuery(academyId);
    }
}

