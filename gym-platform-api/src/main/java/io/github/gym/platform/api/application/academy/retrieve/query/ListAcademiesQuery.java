package io.github.gym.platform.api.application.academy.retrieve.query;

public record ListAcademiesQuery(Boolean active) {

    public static ListAcademiesQuery all() {
        return new ListAcademiesQuery(null);
    }

    public static ListAcademiesQuery with(final Boolean active) {
        return new ListAcademiesQuery(active);
    }
}

