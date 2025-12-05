package io.github.gym.platform.api.application.workout.create;

import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotificationException;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import io.github.gym.platform.api.domain.workout.Workout;
import io.github.gym.platform.api.domain.workout.WorkoutGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CreateWorkoutUseCaseImpl implements CreateWorkoutUseCase {

    private final WorkoutGateway workoutGateway;
    private final MemberGateway memberGateway;
    private final AcademyGateway academyGateway;
    private final TeacherGateway teacherGateway;

    public CreateWorkoutUseCaseImpl(
        final WorkoutGateway workoutGateway,
        final MemberGateway memberGateway,
        final AcademyGateway academyGateway,
        final TeacherGateway teacherGateway
    ) {
        this.workoutGateway = Objects.requireNonNull(workoutGateway);
        this.memberGateway = Objects.requireNonNull(memberGateway);
        this.academyGateway = Objects.requireNonNull(academyGateway);
        this.teacherGateway = Objects.requireNonNull(teacherGateway);
    }

    @Override
    @Transactional
    public WorkoutOutput execute(final CreateWorkoutCommand command) {
        final var memberId = MemberID.from(command.memberId());
        final var academyId = AcademyID.from(command.academyId());
        final var teacherId = TeacherID.from(command.teacherId());

        memberGateway.existById(memberId);
        academyGateway.existById(academyId);
        teacherGateway.existById(teacherId);

        final var workout = Workout.newWorkout(
            memberId,
            academyId,
            teacherId,
            command.name(),
            command.objective(),
            command.observations(),
            command.startAt(),
            command.endAt()
        );

        final var notification = Notification.create();

        workout.validate(notification);

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Workout", notification);
        }

        final var saved = workoutGateway.save(workout);

        return WorkoutOutput.from(saved);
    }
}
