package io.github.gym.platform.api.application.academy.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.retrieve.query.ListAcademiesQuery;

import java.util.List;

public interface ListAcademiesUseCase extends QueryUseCase<ListAcademiesQuery, List<AcademyOutput>> {
}

