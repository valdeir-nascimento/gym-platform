package io.github.gym.platform.api.application.plan.retrieve.query;

public record ListPlansByAcademyQuery(String academyId) {

    public static ListPlansByAcademyQuery with(final String academyId) {
        return new ListPlansByAcademyQuery(academyId);
    }
}






