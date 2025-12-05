package io.github.gym.platform.api.domain.workout;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

import java.time.Instant;

public class Workout extends AggregateRoot<WorkoutID> {

    private final MemberID memberId;
    private final AcademyID academyId;
    private final TeacherID teacherId;

    private final String name;
    private final String objective;
    private final String observations;

    private final WorkoutStatus status;
    private final Boolean active;
    private final Instant startAt;
    private final Instant endAt;

    private final Instant createdAt;
    private final Instant updatedAt;

    private Workout(
        final WorkoutID id,
        final MemberID memberId,
        final AcademyID academyId,
        final TeacherID teacherId,
        final String name,
        final String objective,
        final String observations,
        final WorkoutStatus status,
        final Boolean active,
        final Instant startAt,
        final Instant endAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        super(id);
        this.memberId = memberId;
        this.academyId = academyId;
        this.teacherId = teacherId;
        this.name = name;
        this.objective = objective;
        this.observations = observations;
        this.status = status;
        this.active = active;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Workout newWorkout(
        final MemberID memberId,
        final AcademyID academyId,
        final TeacherID teacherId,
        final String name,
        final String objective,
        final String observations,
        final Instant startAt,
        final Instant endAt
    ) {
        final var now = Instant.now();
        final var id = WorkoutID.unique();

        return new Workout(
            id,
            memberId,
            academyId,
            teacherId,
            name,
            objective,
            observations,
            WorkoutStatus.SCHEDULED,
            Boolean.TRUE,
            startAt,
            endAt,
            now,
            now
        );
    }

    public static Workout with(
        final WorkoutID id,
        final MemberID memberId,
        final AcademyID academyId,
        final TeacherID teacherId,
        final String name,
        final String objective,
        final String observations,
        final WorkoutStatus status,
        final Boolean active,
        final Instant startAt,
        final Instant endAt,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        return new Workout(
            id,
            memberId,
            academyId,
            teacherId,
            name,
            objective,
            observations,
            status,
            active,
            startAt,
            endAt,
            createdAt,
            updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new WorkoutValidator(this, handler).validate();
    }

    public MemberID getMemberId() {
        return memberId;
    }

    public AcademyID getAcademyId() {
        return academyId;
    }

    public TeacherID getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public String getObjective() {
        return objective;
    }

    public String getObservations() {
        return observations;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public Boolean isActive() {
        return active;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Workout updateDetails(
        final String newName,
        final String newObjective,
        final String newObservations,
        final Boolean newActive
    ) {
        final var resolvedActive = (newActive != null) ? newActive : isActive();

        return new Workout(
            getId(),
            getMemberId(),
            getAcademyId(),
            getTeacherId(),
            newName,
            newObjective,
            newObservations,
            getStatus(),
            resolvedActive,
            getStartAt(),
            getEndAt(),
            getCreatedAt(),
            Instant.now()
        );
    }

    public Workout updateSchedule(final Instant newStartAt, final Instant newEndAt) {
        return new Workout(
            getId(),
            getMemberId(),
            getAcademyId(),
            getTeacherId(),
            getName(),
            getObjective(),
            getObservations(),
            getStatus(),
            isActive(),
            newStartAt,
            newEndAt,
            getCreatedAt(),
            Instant.now()
        );
    }

    public Workout updateTeacher(final TeacherID newTeacherId) {
        return new Workout(
            getId(),
            getMemberId(),
            getAcademyId(),
            newTeacherId,
            getName(),
            getObjective(),
            getObservations(),
            getStatus(),
            isActive(),
            getStartAt(),
            getEndAt(),
            getCreatedAt(),
            Instant.now()
        );
    }

    public Workout updateStatus(final WorkoutStatus newStatus) {
        return new Workout(
            getId(),
            getMemberId(),
            getAcademyId(),
            getTeacherId(),
            getName(),
            getObjective(),
            getObservations(),
            newStatus,
            isActive(),
            getStartAt(),
            getEndAt(),
            getCreatedAt(),
            Instant.now()
        );
    }
}
