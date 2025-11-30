package io.github.gym.platform.api.domain.plan;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.validation.ValidationHandler;
import java.math.BigDecimal;
import java.time.Instant;

public class Plan extends AggregateRoot<PlanID> {

    private final AcademyID academyId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final int billingPeriodInMonths;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Plan(
        final PlanID id,
        final AcademyID academyId,
        final String name,
        final String description,
        final BigDecimal price,
        final int billingPeriodInMonths,
        final boolean active,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        super(id);
        this.academyId = academyId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.billingPeriodInMonths = billingPeriodInMonths;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Plan newPlan(
        final AcademyID academyId,
        final String name,
        final String description,
        final BigDecimal price,
        final int billingPeriodInMonths
    ) {
        final var now = Instant.now();
        final var id = PlanID.unique();

        return new Plan(
            id,
            academyId,
            name,
            description,
            price,
            billingPeriodInMonths,
            true,
            now,
            now
        );
    }

    public static Plan with(
        final PlanID id,
        final AcademyID academyId,
        final String name,
        final String description,
        final BigDecimal price,
        final int billingPeriodInMonths,
        final boolean active,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        return new Plan(
            id,
            academyId,
            name,
            description,
            price,
            billingPeriodInMonths,
            active,
            createdAt,
            updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new PlanValidator(this, handler).validate();
    }

    public AcademyID getAcademyId() {
        return academyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getBillingPeriodInMonths() {
        return billingPeriodInMonths;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
