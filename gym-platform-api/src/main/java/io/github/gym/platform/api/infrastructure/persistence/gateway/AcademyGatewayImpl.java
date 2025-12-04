package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.infrastructure.persistence.entity.AcademyEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.AcademyJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AcademyGatewayImpl implements AcademyGateway {

    private final AcademyJpaRepository academyJpaRepository;

    public AcademyGatewayImpl(final AcademyJpaRepository academyJpaRepository) {
        this.academyJpaRepository = academyJpaRepository;
    }

    @Override
    @Transactional
    public Academy save(final Academy academy) {
        final var entity = academyJpaRepository
            .findById(academy.getId().getValue())
            .map(e -> e.updateFrom(academy))
            .orElseGet(() -> AcademyEntity.from(academy));
        return academyJpaRepository.save(entity).toAggregate();
    }

    @Override
    @Transactional(readOnly = true)
    public Academy findById(final AcademyID id) {
        return academyJpaRepository.findById(id.getValue())
            .map(AcademyEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Academy.class, id.getValue()));
    }

    @Override
    @Transactional(readOnly = true)
    public Academy findByCnpj(final String cnpj) {
        return academyJpaRepository.findByCnpj(cnpj)
            .map(AcademyEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Academy.class, cnpj));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCnpj(final String cnpj) {
        return academyJpaRepository.existsByCnpj(cnpj);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Academy> findAll() {
        return academyJpaRepository.findAll()
            .stream()
            .map(AcademyEntity::toAggregate)
            .toList();
    }
}
