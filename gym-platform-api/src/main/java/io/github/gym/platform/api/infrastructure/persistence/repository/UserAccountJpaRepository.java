package io.github.gym.platform.api.infrastructure.persistence.repository;

import io.github.gym.platform.api.infrastructure.persistence.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountJpaRepository extends JpaRepository<UserAccountEntity, UUID> {

    Optional<UserAccountEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}

