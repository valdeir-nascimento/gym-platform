package io.github.gym.platform.api.presentation.rest.controller.academy;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.create.CreateAcademyUseCase;
import io.github.gym.platform.api.application.academy.create.command.CreateAcademyCommand;
import io.github.gym.platform.api.application.academy.retrieve.GetAcademyByCnpjUseCase;
import io.github.gym.platform.api.application.academy.retrieve.GetAcademyByIdUseCase;
import io.github.gym.platform.api.application.academy.retrieve.ListAcademiesUseCase;
import io.github.gym.platform.api.application.academy.retrieve.query.GetAcademyByCnpjQuery;
import io.github.gym.platform.api.application.academy.retrieve.query.GetAcademyByIdQuery;
import io.github.gym.platform.api.application.academy.retrieve.query.ListAcademiesQuery;
import io.github.gym.platform.api.application.academy.update.UpdateAcademyUseCase;
import io.github.gym.platform.api.application.academy.update.command.UpdateAcademyCommand;
import io.github.gym.platform.api.presentation.rest.controller.academy.request.CreateAcademyRequest;
import io.github.gym.platform.api.presentation.rest.controller.academy.request.UpdateAcademyRequest;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.github.gym.platform.api.infrastructure.security.annotation.RoleAdminOrTeacher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/academies")
public class AcademyController {

    private final CreateAcademyUseCase createAcademyUseCase;
    private final GetAcademyByIdUseCase getAcademyByIdUseCase;
    private final GetAcademyByCnpjUseCase getAcademyByCnpjUseCase;
    private final ListAcademiesUseCase listAcademiesUseCase;
    private final UpdateAcademyUseCase updateAcademyUseCase;

    public AcademyController(
        final CreateAcademyUseCase createAcademyUseCase,
        final GetAcademyByIdUseCase getAcademyByIdUseCase,
        final GetAcademyByCnpjUseCase getAcademyByCnpjUseCase,
        final UpdateAcademyUseCase updateAcademyUseCase,
        final ListAcademiesUseCase listAcademiesUseCase
    ) {
        this.createAcademyUseCase = createAcademyUseCase;
        this.getAcademyByIdUseCase = getAcademyByIdUseCase;
        this.getAcademyByCnpjUseCase = getAcademyByCnpjUseCase;
        this.updateAcademyUseCase = updateAcademyUseCase;
        this.listAcademiesUseCase = listAcademiesUseCase;
    }

    @GetMapping
    @RoleAdminOrTeacher
    public ResponseEntity<List<AcademyOutput>> listAcademies(@RequestParam(required = false) final Boolean active) {
        final var query = active == null ? ListAcademiesQuery.all() : ListAcademiesQuery.with(active);
        final var outputs = listAcademiesUseCase.execute(query);
        return ResponseEntity.ok(outputs);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AcademyOutput> createAcademy(@RequestBody @Valid final CreateAcademyRequest request) {
        final var command = CreateAcademyCommand.with(
            request.name(),
            request.cnpj(),
            request.phone(),
            request.email(),
            request.address()
        );

        final var output = createAcademyUseCase.execute(command);

        final var location = ApiUriFactory.createdLocation(
            "/academies/{academyId}",
            output.id()
        );

        return ResponseEntity.created(location).body(output);
    }

    @GetMapping("/{academyId}")
    @RoleAdminOrTeacher
    public ResponseEntity<AcademyOutput> getAcademyById(@PathVariable final String academyId) {
        final var query = GetAcademyByIdQuery.with(academyId);
        final var output = getAcademyByIdUseCase.execute(query);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/cnpj/{cnpj}")
    @RoleAdminOrTeacher
    public ResponseEntity<AcademyOutput> getAcademyByCnpj(@PathVariable final String cnpj) {
        final var query = GetAcademyByCnpjQuery.with(cnpj);
        final var output = getAcademyByCnpjUseCase.execute(query);
        return ResponseEntity.ok(output);
    }

    @PutMapping("/{academyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AcademyOutput> updateAcademy(@PathVariable final String academyId, @RequestBody @Valid final UpdateAcademyRequest request) {
        final var command = UpdateAcademyCommand.with(
            academyId,
            request.name(),
            request.cnpj(),
            request.phone(),
            request.email(),
            request.address(),
            request.active()
        );

        final var output = updateAcademyUseCase.execute(command);

        return ResponseEntity.ok(output);
    }
}
