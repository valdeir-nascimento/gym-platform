package io.github.gym.platform.api.infrastructure.persistence.repository;

import io.github.gym.platform.api.infrastructure.persistence.entity.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutJpaRepository extends JpaRepository<WorkoutEntity, UUID> {
}
