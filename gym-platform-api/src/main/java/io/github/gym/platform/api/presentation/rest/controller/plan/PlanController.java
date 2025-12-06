package io.github.gym.platform.api.presentation.rest.controller.plan;

import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.create.CreatePlanUseCase;
import io.github.gym.platform.api.application.plan.create.command.CreatePlanCommand;
import io.github.gym.platform.api.application.plan.retrieve.GetPlanByIdUseCase;
import io.github.gym.platform.api.application.plan.retrieve.ListPlansByAcademyUseCase;
import io.github.gym.platform.api.application.plan.retrieve.query.GetPlanByIdQuery;
import io.github.gym.platform.api.application.plan.retrieve.query.ListPlansByAcademyQuery;
import io.github.gym.platform.api.infrastructure.security.annotation.RoleAdminOrTeacher;
import io.github.gym.platform.api.presentation.rest.controller.plan.request.CreatePlanRequest;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/plans")
public class PlanController {

    private final CreatePlanUseCase createPlanUseCase;
    private final GetPlanByIdUseCase getPlanByIdUseCase;
    private final ListPlansByAcademyUseCase listPlansByAcademyUseCase;

    public PlanController(
        final CreatePlanUseCase createPlanUseCase,
        final GetPlanByIdUseCase getPlanByIdUseCase,
        final ListPlansByAcademyUseCase listPlansByAcademyUseCase
    ) {
        this.createPlanUseCase = createPlanUseCase;
        this.getPlanByIdUseCase = getPlanByIdUseCase;
        this.listPlansByAcademyUseCase = listPlansByAcademyUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanOutput> createPlan(@RequestBody @Valid final CreatePlanRequest request) {
        final var command = CreatePlanCommand.with(
            request.academyId(),
            request.name(),
            request.description(),
            request.price(),
            request.billingPeriodInMonths()
        );

        final var output = createPlanUseCase.execute(command);
        final var location = ApiUriFactory.createdLocation("/plans/{planId}", output.id());
        return ResponseEntity.created(location).body(output);
    }

    @GetMapping("/{planId}")
    @RoleAdminOrTeacher
    public ResponseEntity<PlanOutput> getPlan(@PathVariable final String planId) {
        final var output = getPlanByIdUseCase.execute(GetPlanByIdQuery.with(planId));
        return ResponseEntity.ok(output);
    }

    @GetMapping("/academy/{academyId}")
    @RoleAdminOrTeacher
    public ResponseEntity<List<PlanOutput>> listPlansByAcademy(@PathVariable final String academyId) {
        final var output = listPlansByAcademyUseCase.execute(ListPlansByAcademyQuery.with(academyId));
        return ResponseEntity.ok(output);
    }
}






