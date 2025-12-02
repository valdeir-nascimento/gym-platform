package io.github.gym.platform.api.application.plan.create;

import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.create.command.CreatePlanCommand;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.plan.Plan;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreatePlanUseCaseImpl implements CreatePlanUseCase {

    private final PlanGateway planGateway;

    public CreatePlanUseCaseImpl(final PlanGateway planGateway) {
        this.planGateway = Objects.requireNonNull(planGateway);
    }

    @Override
    public PlanOutput execute(final CreatePlanCommand command) {
        final var plan = Plan.newPlan(
            AcademyID.from(command.academyId()),
            command.name(),
            command.description(),
            command.price(),
            command.billingPeriodInMonths()
        );

        final var notification = Notification.create();

        plan.validate(notification);

        if (notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        final var saved = planGateway.save(plan);

        return PlanOutput.from(saved);
    }
}



