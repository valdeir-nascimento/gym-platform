package io.github.gym.platform.api.presentation.rest.controller.teacher;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.gym.platform.api.application.teacher.register.RegisterTeacherUseCase;
import io.github.gym.platform.api.application.teacher.register.command.RegisterTeacherCommand;
import io.github.gym.platform.api.application.teacher.retrieve.ListTeachersUseCase;
import io.github.gym.platform.api.application.teacher.retrieve.query.ListTeachersQuery;
import io.github.gym.platform.api.infrastructure.security.annotation.RoleAdminOrTeacher;
import io.github.gym.platform.api.presentation.rest.controller.teacher.request.RegisterTeacherRequest;
import io.github.gym.platform.api.presentation.rest.controller.teacher.response.TeacherResponse;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/teachers")
public class TeacherController {

    private final RegisterTeacherUseCase registerTeacherUseCase;
    private final ListTeachersUseCase listTeachersUseCase;

    public TeacherController(
        final RegisterTeacherUseCase registerTeacherUseCase,
        final ListTeachersUseCase listTeachersUseCase
    ) {
        this.registerTeacherUseCase = registerTeacherUseCase;
        this.listTeachersUseCase = listTeachersUseCase;
    }

    @GetMapping
    @RoleAdminOrTeacher
    public ResponseEntity<List<TeacherResponse>> listTeachers(@RequestParam final String academyId) {
        final var outputs = listTeachersUseCase.execute(ListTeachersQuery.with(academyId));
        final var response = outputs.stream().map(TeacherResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherResponse> registerTeacher(@RequestBody @Valid final RegisterTeacherRequest request) {
        final var command = RegisterTeacherCommand.with(
            request.fullName(),
            request.email(),
            request.phone(),
            request.cpf(),
            request.birthDate(),
            request.password(),
            request.academyId(),
            request.specialization()
        );

        final var output = registerTeacherUseCase.execute(command);
        final var location = ApiUriFactory.createdLocation("/teachers/{teacherId}", output.id());
        return ResponseEntity.created(location).body(TeacherResponse.from(null));
    }
}

