package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.plan.Plan;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.infrastructure.persistence.entity.PlanEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.PlanJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PlanGatewayImpl implements PlanGateway {

    private final PlanJpaRepository planJpaRepository;

    public PlanGatewayImpl(final PlanJpaRepository planJpaRepository) {
        this.planJpaRepository = Objects.requireNonNull(planJpaRepository);
    }

    @Override
    public Plan save(final Plan plan) {
        final var entity = planJpaRepository
            .findById(plan.getId().getValue())
            .map(e -> e.updateFrom(plan))
            .orElseGet(() -> PlanEntity.from(plan));
        return planJpaRepository.save(entity).toAggregate();
    }

    @Override
    public Plan findById(final PlanID id) {
        return planJpaRepository.findById(id.getValue())
            .map(PlanEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Plan.class, id.getValue()));
    }

    @Override
    public List<Plan> findAllByAcademy(final AcademyID academyId) {
        return planJpaRepository.findAllByAcademyId(academyId.getValue())
            .stream()
            .map(PlanEntity::toAggregate)
            .toList();
    }
}
