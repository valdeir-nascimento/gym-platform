package io.github.gym.platform.api.application.plan.create.command;

import java.math.BigDecimal;

public record CreatePlanCommand(
    String academyId,
    String name,
    String description,
    BigDecimal price,
    int billingPeriodInMonths
) {

    public static CreatePlanCommand with(
        final String academyId,
        final String name,
        final String description,
        final BigDecimal price,
        final int billingPeriodInMonths
    ) {
        return new CreatePlanCommand(academyId, name, description, price, billingPeriodInMonths);
    }
}




