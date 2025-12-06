package io.github.gym.platform.api.application.workout.retrieve;

import io.github.gym.platform.api.application.workout.create.WorkoutOutput;
import io.github.gym.platform.api.domain.workout.WorkoutGateway;
import io.github.gym.platform.api.domain.workout.WorkoutID;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GetWorkoutByIdUseCaseImpl implements GetWorkoutByIdUseCase {

    private final WorkoutGateway workoutGateway;

    public GetWorkoutByIdUseCaseImpl(final WorkoutGateway workoutGateway) {
        this.workoutGateway = Objects.requireNonNull(workoutGateway);
    }

    @Override
    public WorkoutOutput execute(final GetWorkoutByIdQuery query) {
        final var workoutId = WorkoutID.from(query.workoutId());
        final var workout = workoutGateway.findById(workoutId);
        return WorkoutOutput.from(workout);
    }
}

