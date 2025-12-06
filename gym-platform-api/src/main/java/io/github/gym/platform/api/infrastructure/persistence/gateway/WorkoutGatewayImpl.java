package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.workout.Workout;
import io.github.gym.platform.api.domain.workout.WorkoutGateway;
import io.github.gym.platform.api.domain.workout.WorkoutID;
import io.github.gym.platform.api.infrastructure.persistence.entity.WorkoutEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.WorkoutJpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
public class WorkoutGatewayImpl implements WorkoutGateway {

    private final WorkoutJpaRepository workoutRepository;
    private final MemberGateway memberGateway;
    private final AcademyGateway academyGateway;
    private final TeacherGateway teacherGateway;

    public WorkoutGatewayImpl(
        final WorkoutJpaRepository workoutRepository,
        final MemberGateway memberGateway,
        final AcademyGateway academyGateway,
        final TeacherGateway teacherGateway
    ) {
        this.workoutRepository = Objects.requireNonNull(workoutRepository);
        this.memberGateway = Objects.requireNonNull(memberGateway);
        this.academyGateway = Objects.requireNonNull(academyGateway);
        this.teacherGateway = Objects.requireNonNull(teacherGateway);
    }

    @Override
    @Transactional
    public Workout save(final @NonNull Workout workout) {
        final var member = memberGateway.findById(workout.getMemberId());
        final var academy = academyGateway.findById(workout.getAcademyId());
        final var teacher = teacherGateway.findById(workout.getTeacherId());

        final var entity = WorkoutEntity.from(workout, member, academy, teacher);

        return workoutRepository.save(entity).toAggregate();
    }

    @Override
    @Transactional(readOnly = true)
    public Workout findById(final WorkoutID id) {
        return workoutRepository.findById(id.getValue())
            .map(WorkoutEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Workout.class, id.getValue()));
    }
}
