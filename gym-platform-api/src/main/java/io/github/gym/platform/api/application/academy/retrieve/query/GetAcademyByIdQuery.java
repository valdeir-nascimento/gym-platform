package io.github.gym.platform.api.application.academy.retrieve.query;

public record GetAcademyByIdQuery(String id) {
    public static GetAcademyByIdQuery with(final String id) {
        return new GetAcademyByIdQuery(id);
    }
}
