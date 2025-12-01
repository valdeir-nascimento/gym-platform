package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.plan.Plan;
import io.github.gym.platform.api.domain.plan.PlanID;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "plan")
public class PlanEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "academy_id", nullable = false, columnDefinition = "uuid")
    private UUID academyId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "billing_period_months", nullable = false)
    private int billingPeriodInMonths;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected PlanEntity() {
        // JPA only
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public UUID getAcademyId() {
        return academyId;
    }

    public void setAcademyId(final UUID academyId) {
        this.academyId = academyId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public int getBillingPeriodInMonths() {
        return billingPeriodInMonths;
    }

    public void setBillingPeriodInMonths(final int billingPeriodInMonths) {
        this.billingPeriodInMonths = billingPeriodInMonths;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PlanEntity that = (PlanEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    public void onPrePersist() {
        final var now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.version == null) {
            this.version = 0L;
        }

        if (!this.active) {
            this.active = true;
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = Instant.now();
    }

    public static PlanEntity from(final Plan plan) {
        final var entity = new PlanEntity();

        if (plan.getId() != null) {
            entity.id = plan.getId().getValue();
        }

        if (plan.getAcademyId() != null) {
            entity.academyId = plan.getAcademyId().getValue();
        }

        entity.name = plan.getName();
        entity.description = plan.getDescription();
        entity.price = plan.getPrice();
        entity.billingPeriodInMonths = plan.getBillingPeriodInMonths();
        entity.active = plan.isActive();

        return entity;
    }

    public Plan toAggregate() {
        return Plan.with(
            PlanID.from(this.id),
            AcademyID.from(this.academyId),
            this.name,
            this.description,
            this.price,
            this.billingPeriodInMonths,
            this.active,
            this.createdAt,
            this.updatedAt
        );
    }

    public PlanEntity updateFrom(final Plan plan) {
        if (plan.getAcademyId() != null) {
            this.academyId = plan.getAcademyId().getValue();
        }
        this.name = plan.getName();
        this.description = plan.getDescription();
        this.price = plan.getPrice();
        this.billingPeriodInMonths = plan.getBillingPeriodInMonths();
        this.active = plan.isActive();
        return this;
    }
}
