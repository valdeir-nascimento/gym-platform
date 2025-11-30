package io.github.gym.platform.api.infrastructure.persistence.repository;

import io.github.gym.platform.api.infrastructure.persistence.entity.AcademyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AcademyJpaRepository extends JpaRepository<AcademyEntity, UUID> {

    Optional<AcademyEntity> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}
