package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.teacher.TeacherStatus;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
    name = "teachers",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_teachers_user_academy",
            columnNames = {"user_account_id", "academy_id"}
        )
    },
    indexes = {
        @Index(name = "idx_teachers_user_account", columnList = "user_account_id"),
        @Index(name = "idx_teachers_academy", columnList = "academy_id"),
        @Index(name = "idx_teachers_status", columnList = "status")
    }
)
public class TeacherJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "specialization", nullable = false, length = 255)
    private String specialization;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TeacherStatus status;

    @Column(name = "hired_at", nullable = false)
    private Instant hiredAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @JoinColumn(name = "academy_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AcademyEntity academy;

    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccountEntity userAccount;

    protected TeacherJpaEntity() {
    }

    private TeacherJpaEntity(
        final UUID id,
        final UserAccountEntity userAccount,
        final AcademyEntity academy,
        final String specialization,
        final TeacherStatus status,
        final Instant hiredAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        this.id = id;
        this.userAccount = userAccount;
        this.academy = academy;
        this.specialization = specialization;
        this.status = status;
        this.hiredAt = hiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public TeacherStatus getStatus() {
        return status;
    }

    public Instant getHiredAt() {
        return hiredAt;
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

    public UserAccountEntity getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccountEntity userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TeacherJpaEntity that = (TeacherJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static TeacherJpaEntity from(
        final Teacher teacher,
        final UserAccount userAccount,
        final Academy academy
    ) {
        return new TeacherJpaEntity(
            teacher.getId().getValue(),
            UserAccountEntity.from(userAccount),
            AcademyEntity.from(academy),
            teacher.getSpecialization(),
            teacher.getStatus(),
            teacher.getHiredAt(),
            teacher.getCreatedAt(),
            teacher.getUpdatedAt()
        );
    }

    public Teacher toAggregate() {
        return Teacher.with(
            TeacherID.from(id.toString()),
            UserAccountID.from(userAccount.getId()),
            AcademyID.from(academy.getId()),
            specialization,
            status,
            hiredAt,
            createdAt,
            updatedAt
        );
    }
}
