package io.github.gym.platform.api.application.academy.retrieve;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.retrieve.query.GetAcademyByIdQuery;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GetAcademyByIdUseCaseImpl implements GetAcademyByIdUseCase {

    private final AcademyGateway academyGateway;

    public GetAcademyByIdUseCaseImpl(final AcademyGateway academyGateway) {
        this.academyGateway = Objects.requireNonNull(academyGateway);
    }

    @Override
    public AcademyOutput execute(final GetAcademyByIdQuery query) {
        final var academy = academyGateway.findById(AcademyID.from(query.id()));
        return AcademyOutput.from(academy);
    }
}
