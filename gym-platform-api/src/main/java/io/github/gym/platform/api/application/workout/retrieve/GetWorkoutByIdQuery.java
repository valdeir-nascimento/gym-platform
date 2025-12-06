package io.github.gym.platform.api.application.workout.retrieve;

public record GetWorkoutByIdQuery(String workoutId) {

    public static GetWorkoutByIdQuery with(final String workoutId) {
        return new GetWorkoutByIdQuery(workoutId);
    }
}

