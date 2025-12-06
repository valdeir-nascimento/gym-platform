package io.github.gym.platform.api.application.workout.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.workout.create.WorkoutOutput;

public interface GetWorkoutByIdUseCase extends QueryUseCase<GetWorkoutByIdQuery, WorkoutOutput> {
}

