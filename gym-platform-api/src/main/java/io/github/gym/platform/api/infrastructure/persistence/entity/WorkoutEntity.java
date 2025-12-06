package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.workout.Workout;
import io.github.gym.platform.api.domain.workout.WorkoutID;
import io.github.gym.platform.api.domain.workout.WorkoutStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "workouts")
public class WorkoutEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "objective", nullable = false, length = 500)
    private String objective;

    @Column(name = "observations")
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkoutStatus status;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "end_at")
    private Instant endAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @JoinColumn(name = "academy_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AcademyEntity academy;

    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MemberJpaEntity member;

    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TeacherJpaEntity teacher;

    protected WorkoutEntity() {
    }

    private WorkoutEntity(
        final UUID id,
        final String name,
        final String objective,
        final String observations,
        final WorkoutStatus status,
        final Boolean active,
        final Instant startAt,
        final Instant endAt,
        final Instant createdAt,
        final Instant updatedAt,
        final AcademyEntity academy,
        final MemberJpaEntity member,
        final TeacherJpaEntity teacher
    ) {
        this.id = id;
        this.name = name;
        this.objective = objective;
        this.observations = observations;
        this.status = status;
        this.active = active;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.academy = academy;
        this.member = member;
        this.teacher = teacher;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public void setStatus(WorkoutStatus status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public void setEndAt(Instant endAt) {
        this.endAt = endAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AcademyEntity getAcademy() {
        return academy;
    }

    public void setAcademy(AcademyEntity academy) {
        this.academy = academy;
    }

    public MemberJpaEntity getMember() {
        return member;
    }

    public void setMember(MemberJpaEntity member) {
        this.member = member;
    }

    public TeacherJpaEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherJpaEntity teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final WorkoutEntity that = (WorkoutEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static WorkoutEntity from(
        final Workout workout,
        final Member member,
        final Academy academy,
        final Teacher teacher
    ) {
        return new WorkoutEntity(
            workout.getId().getValue(),
            workout.getName(),
            workout.getObjective(),
            workout.getObservations(),
            workout.getStatus(),
            workout.isActive(),
            workout.getStartAt(),
            workout.getEndAt(),
            workout.getCreatedAt(),
            workout.getUpdatedAt(),
            AcademyEntity.from(academy),
            MemberJpaEntity.from(member, null, null, null),
            TeacherJpaEntity.from(teacher, null, null)
        );
    }

    public Workout toAggregate() {
        return Workout.with(
            WorkoutID.from(id.toString()),
            MemberID.from(member.getId()),
            AcademyID.from(academy.getId()),
            TeacherID.from(teacher.getId()),
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
}
