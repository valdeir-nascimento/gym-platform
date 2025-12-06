package io.github.gym.platform.api.presentation.rest.controller.workout;

import io.github.gym.platform.api.application.workout.create.CreateWorkoutCommand;
import io.github.gym.platform.api.application.workout.create.CreateWorkoutUseCase;
import io.github.gym.platform.api.application.workout.create.WorkoutOutput;
import io.github.gym.platform.api.application.workout.retrieve.GetWorkoutByIdQuery;
import io.github.gym.platform.api.application.workout.retrieve.GetWorkoutByIdUseCase;
import io.github.gym.platform.api.infrastructure.security.annotation.RoleAdminOrTeacher;
import io.github.gym.platform.api.presentation.rest.controller.workout.request.CreateWorkoutRequest;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/workouts")
public class WorkoutController {

    private final CreateWorkoutUseCase createWorkoutUseCase;
    private final GetWorkoutByIdUseCase getWorkoutByIdUseCase;

    public WorkoutController(
        final CreateWorkoutUseCase createWorkoutUseCase,
        final GetWorkoutByIdUseCase getWorkoutByIdUseCase
    ) {
        this.createWorkoutUseCase = createWorkoutUseCase;
        this.getWorkoutByIdUseCase = getWorkoutByIdUseCase;
    }

    @PostMapping
    @RoleAdminOrTeacher
    public ResponseEntity<WorkoutOutput> createWorkout(
        @RequestBody @Valid final CreateWorkoutRequest request
    ) {
        final var command = CreateWorkoutCommand.with(
            request.memberId(),
            request.academyId(),
            request.teacherId(),
            request.name(),
            request.objective(),
            request.observations(),
            request.startAt(),
            request.endAt()
        );

        final var output = createWorkoutUseCase.execute(command);
        final var location = ApiUriFactory.createdLocation("/workouts/{workoutId}", output.id());
        return ResponseEntity.created(location).body(output);
    }

    @GetMapping("/{workoutId}")
    @RoleAdminOrTeacher
    public ResponseEntity<WorkoutOutput> getWorkout(@PathVariable final String workoutId) {
        final var output = getWorkoutByIdUseCase.execute(GetWorkoutByIdQuery.with(workoutId));
        return ResponseEntity.ok(output);
    }
}

