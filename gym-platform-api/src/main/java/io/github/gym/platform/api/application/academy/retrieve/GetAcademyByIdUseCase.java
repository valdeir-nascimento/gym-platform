package io.github.gym.platform.api.application.academy.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.retrieve.query.GetAcademyByIdQuery;

public interface GetAcademyByIdUseCase
        extends QueryUseCase<GetAcademyByIdQuery, AcademyOutput> {
}
