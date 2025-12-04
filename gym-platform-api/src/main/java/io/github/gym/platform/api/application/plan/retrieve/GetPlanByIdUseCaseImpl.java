package io.github.gym.platform.api.application.plan.retrieve;

import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.retrieve.query.GetPlanByIdQuery;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.plan.PlanID;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GetPlanByIdUseCaseImpl implements GetPlanByIdUseCase {

    private final PlanGateway planGateway;

    public GetPlanByIdUseCaseImpl(final PlanGateway planGateway) {
        this.planGateway = Objects.requireNonNull(planGateway);
    }

    @Override
    public PlanOutput execute(final GetPlanByIdQuery query) {
        final var plan = planGateway.findById(PlanID.from(query.planId()));
        return PlanOutput.from(plan);
    }
}