package io.github.gym.platform.api.application.plan;

import io.github.gym.platform.api.domain.plan.Plan;

import java.math.BigDecimal;
import java.time.Instant;

public record PlanOutput(
    String id,
    String academyId,
    String name,
    String description,
    BigDecimal price,
    int billingPeriodInMonths,
    boolean active,
    Instant createdAt,
    Instant updatedAt
) {

    public static PlanOutput from(final Plan plan) {
        return new PlanOutput(
            plan.getId().getValue().toString(),
            plan.getAcademyId().getValue().toString(),
            plan.getName(),
            plan.getDescription(),
            plan.getPrice(),
            plan.getBillingPeriodInMonths(),
            plan.isActive(),
            plan.getCreatedAt(),
            plan.getUpdatedAt()
        );
    }
}
