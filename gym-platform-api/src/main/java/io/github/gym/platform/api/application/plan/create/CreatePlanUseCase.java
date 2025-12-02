package io.github.gym.platform.api.application.plan.create;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.create.command.CreatePlanCommand;

public interface CreatePlanUseCase extends CommandUseCase<CreatePlanCommand, PlanOutput> {
}



