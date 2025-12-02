package io.github.gym.platform.api.application.plan.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.retrieve.query.GetPlanByIdQuery;

public interface GetPlanByIdUseCase extends QueryUseCase<GetPlanByIdQuery, PlanOutput> {
}



