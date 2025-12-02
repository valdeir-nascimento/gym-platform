package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.teacher.TeacherStatus;
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

    @Column(name = "user_account_id", nullable = false)
    private UUID userAccountId;

    @Column(name = "academy_id", nullable = false)
    private UUID academyId;

    @Column(name = "specialization", nullable = false, length = 255)
    private String specialization;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TeacherStatus status;

    @Column(name = "hired_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant hiredAt;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    protected TeacherJpaEntity() {
    }

    private TeacherJpaEntity(
        final UUID id,
        final UUID userAccountId,
        final UUID academyId,
        final String specialization,
        final TeacherStatus status,
        final Instant hiredAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        this.id = id;
        this.userAccountId = userAccountId;
        this.academyId = academyId;
        this.specialization = specialization;
        this.status = status;
        this.hiredAt = hiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public static TeacherJpaEntity from(final Teacher teacher) {
        return new TeacherJpaEntity(
            teacher.getId().getValue(),
            teacher.getUserAccountId().getValue(),
            teacher.getAcademyId().getValue(),
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
            UserAccountID.from(userAccountId.toString()),
            AcademyID.from(academyId.toString()),
            specialization,
            status,
            hiredAt,
            createdAt,
            updatedAt
        );
    }
}
