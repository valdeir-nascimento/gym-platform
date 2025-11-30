package io.github.gym.platform.api.domain.member;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

import java.time.Instant;

public class Member extends AggregateRoot<MemberID> {

    private final UserAccountID userAccountId;
    private final AcademyID academyId;
    private final PlanID planId;
    private final MemberStatus status;
    private final Instant joinedAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Member(
        final MemberID id,
        final UserAccountID userAccountId,
        final AcademyID academyId,
        final PlanID planId,
        final MemberStatus status,
        final Instant joinedAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        super(id);
        this.userAccountId = userAccountId;
        this.academyId = academyId;
        this.planId = planId;
        this.status = status;
        this.joinedAt = joinedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Member newMember(
        final UserAccountID userAccountId,
        final AcademyID academyId,
        final PlanID planId
    ) {
        final var now = Instant.now();
        final var id = MemberID.unique();

        return new Member(
            id,
            userAccountId,
            academyId,
            planId,
            MemberStatus.ACTIVE,
            now,
            now,
            now
        );
    }

    public static Member with(
        final MemberID id,
        final UserAccountID userAccountId,
        final AcademyID academyId,
        final PlanID planId,
        final MemberStatus status,
        final Instant joinedAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        return new Member(
            id,
            userAccountId,
            academyId,
            planId,
            status,
            joinedAt,
            createdAt,
            updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new MemberValidator(this, handler).validate();
    }

    public UserAccountID getUserAccountId() {
        return userAccountId;
    }

    public AcademyID getAcademyId() {
        return academyId;
    }

    public PlanID getPlanId() {
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
