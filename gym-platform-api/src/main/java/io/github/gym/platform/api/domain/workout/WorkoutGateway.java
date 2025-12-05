package io.github.gym.platform.api.domain.workout;

public interface WorkoutGateway {
    Workout save(Workout workout);

    Workout findById(WorkoutID id);

}
