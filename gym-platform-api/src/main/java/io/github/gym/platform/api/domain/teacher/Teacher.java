package io.github.gym.platform.api.domain.teacher;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

import java.time.Instant;

public class Teacher extends AggregateRoot<TeacherID> {

    private final UserAccountID userAccountId;
    private final AcademyID academyId;
    private final String specialization;
    private final TeacherStatus status;
    private final Instant hiredAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Teacher(
        final TeacherID id,
        final UserAccountID userAccountId,
        final AcademyID academyId,
        final String specialization,
        final TeacherStatus status,
        final Instant hiredAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        super(id);
        this.userAccountId = userAccountId;
        this.academyId = academyId;
        this.specialization = specialization;
        this.status = status;
        this.hiredAt = hiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Teacher newTeacher(
        final UserAccountID userAccountId,
        final AcademyID academyId,
        final String specialization
    ) {
        final var now = Instant.now();
        final var id = TeacherID.unique();

        return new Teacher(
            id,
            userAccountId,
            academyId,
            specialization,
            TeacherStatus.ACTIVE, // regra de negócio inicial
            now,
            now,
            now
        );
    }

    /**
     * Factory method to rehydrate an existing Teacher from persistence.
     */
    public static Teacher with(
        final TeacherID id,
        final UserAccountID userAccountId,
        final AcademyID academyId,
        final String specialization,
        final TeacherStatus status,
        final Instant hiredAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        return new Teacher(
            id,
            userAccountId,
            academyId,
            specialization,
            status,
            hiredAt,
            createdAt,
            updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new TeacherValidator(this, handler).validate();
    }

    public UserAccountID getUserAccountId() {
        return userAccountId;
    }

    public AcademyID getAcademyId() {
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
}
