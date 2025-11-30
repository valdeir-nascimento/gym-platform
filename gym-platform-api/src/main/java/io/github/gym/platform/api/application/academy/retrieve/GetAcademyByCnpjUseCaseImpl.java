package io.github.gym.platform.api.application.academy.retrieve;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.retrieve.query.GetAcademyByCnpjQuery;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GetAcademyByCnpjUseCaseImpl implements GetAcademyByCnpjUseCase {

    private final AcademyGateway academyGateway;

    public GetAcademyByCnpjUseCaseImpl(final AcademyGateway academyGateway) {
        this.academyGateway = Objects.requireNonNull(academyGateway);
    }

    @Override
    public AcademyOutput execute(final GetAcademyByCnpjQuery query) {
        final var academy = academyGateway.findByCnpj(query.cnpj());
        return AcademyOutput.from(academy);
    }
}
