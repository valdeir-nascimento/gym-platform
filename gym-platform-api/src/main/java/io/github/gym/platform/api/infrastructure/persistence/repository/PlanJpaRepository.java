package io.github.gym.platform.api.infrastructure.persistence.repository;

import io.github.gym.platform.api.infrastructure.persistence.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, UUID> {

    List<PlanEntity> findAllByAcademyId(UUID academyId);
}
