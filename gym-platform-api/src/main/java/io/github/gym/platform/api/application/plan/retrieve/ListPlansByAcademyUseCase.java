package io.github.gym.platform.api.application.plan.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.plan.retrieve.query.ListPlansByAcademyQuery;

import java.util.List;

public interface ListPlansByAcademyUseCase extends QueryUseCase<ListPlansByAcademyQuery, List<PlanOutput>> {
}





