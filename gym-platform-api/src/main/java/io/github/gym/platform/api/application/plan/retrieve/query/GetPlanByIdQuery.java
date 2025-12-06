package io.github.gym.platform.api.application.plan.retrieve.query;

public record GetPlanByIdQuery(String planId) {

    public static GetPlanByIdQuery with(final String planId) {
        return new GetPlanByIdQuery(planId);
    }
}






