package io.github.gym.platform.api.application.academy.retrieve;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.retrieve.query.ListAcademiesQuery;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAcademiesUseCaseImpl implements ListAcademiesUseCase {

    private final AcademyGateway academyGateway;

    public ListAcademiesUseCaseImpl(final AcademyGateway academyGateway) {
        this.academyGateway = academyGateway;
    }

    @Override
    public List<AcademyOutput> execute(final ListAcademiesQuery query) {
        final Boolean activeFilter = query.active();
        return academyGateway.findAll()
            .stream()
            .filter(academy -> activeFilter == null || academy.isActive() == activeFilter)
            .map(AcademyOutput::from)
            .toList();
    }
}

