package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.member.MemberStatus;
import io.github.gym.platform.api.domain.plan.Plan;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "members")
public class MemberJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MemberStatus status;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @JoinColumn(name = "academy_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AcademyEntity academy;

    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PlanEntity plan;

    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccountEntity userAccount;

    protected MemberJpaEntity() {
    }

    private MemberJpaEntity(
        final UUID id,
        final UserAccountEntity userAccount,
        final AcademyEntity academy,
        final PlanEntity plan,
        final MemberStatus status,
        final Instant joinedAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        this.id = id;
        this.userAccount = userAccount;
        this.academy = academy;
        this.plan = plan;
        this.status = status;
        this.joinedAt = joinedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
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

    public AcademyEntity getAcademy() {
        return academy;
    }

    public void setAcademy(AcademyEntity academy) {
        this.academy = academy;
    }

    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }

    public UserAccountEntity getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccountEntity userAccount) {
        this.userAccount = userAccount;
    }

    public static MemberJpaEntity from(
        final Member member,
        final UserAccount userAccount,
        final Academy academy,
        final Plan plan
    ) {
        return new MemberJpaEntity(
            member.getId().getValue(),
            UserAccountEntity.from(userAccount),
            AcademyEntity.from(academy),
            PlanEntity.from(plan),
            member.getStatus(),
            member.getJoinedAt(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }

    public Member toAggregate() {
        return Member.with(
            MemberID.from(id.toString()),
            UserAccountID.from(userAccount.getId()),
            AcademyID.from(academy.getId()),
            PlanID.from(plan.getId()),
            status,
            joinedAt,
            createdAt,
            updatedAt
        );
    }
}
