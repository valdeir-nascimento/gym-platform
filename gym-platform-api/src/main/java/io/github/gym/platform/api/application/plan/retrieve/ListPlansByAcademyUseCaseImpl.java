package io.github.gym.platform.api.application.plan.retrieve;

import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.retrieve.query.ListPlansByAcademyQuery;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ListPlansByAcademyUseCaseImpl implements ListPlansByAcademyUseCase {

    private final PlanGateway planGateway;

    public ListPlansByAcademyUseCaseImpl(final PlanGateway planGateway) {
        this.planGateway = Objects.requireNonNull(planGateway);
    }

    @Override
    public List<PlanOutput> execute(final ListPlansByAcademyQuery query) {
        final var academyId = AcademyID.from(query.academyId());
        return planGateway.findAllByAcademy(academyId).stream()
            .map(PlanOutput::from)
            .toList();
    }
}
