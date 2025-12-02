package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.member.MemberStatus;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "members",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_members_user_academy",
            columnNames = {"user_account_id", "academy_id"}
        )
    },
    indexes = {
        @Index(name = "idx_members_user_account", columnList = "user_account_id"),
        @Index(name = "idx_members_academy", columnList = "academy_id"),
        @Index(name = "idx_members_plan", columnList = "plan_id")
    }
)
public class MemberJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_account_id", nullable = false)
    private UUID userAccountId;

    @Column(name = "academy_id", nullable = false)
    private UUID academyId;

    @Column(name = "plan_id", nullable = false)
    private UUID planId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MemberStatus status;

    @Column(name = "joined_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant joinedAt;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    protected MemberJpaEntity() {
    }

    private MemberJpaEntity(
        final UUID id,
        final UUID userAccountId,
        final UUID academyId,
        final UUID planId,
        final MemberStatus status,
        final Instant joinedAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        this.id = id;
        this.userAccountId = userAccountId;
        this.academyId = academyId;
        this.planId = planId;
        this.status = status;
        this.joinedAt = joinedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberJpaEntity from(final Member member) {
        return new MemberJpaEntity(
            member.getId().getValue(),
            member.getUserAccountId().getValue(),
            member.getAcademyId().getValue(),
            member.getPlanId().getValue(),
            member.getStatus(),
            member.getJoinedAt(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }

    public Member toAggregate() {
        return Member.with(
            MemberID.from(id.toString()),
            UserAccountID.from(userAccountId.toString()),
            AcademyID.from(academyId.toString()),
            PlanID.from(planId.toString()),
            status,
            joinedAt,
            createdAt,
            updatedAt
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public UUID getAcademyId() {
        return academyId;
    }

    public UUID getPlanId() {
        return planId;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
