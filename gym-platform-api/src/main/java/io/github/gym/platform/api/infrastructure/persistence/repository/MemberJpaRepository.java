package io.github.gym.platform.api.infrastructure.persistence.repository;

import io.github.gym.platform.api.infrastructure.persistence.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, UUID> {

    Optional<MemberJpaEntity> findByUserAccountIdAndAcademyId(UUID userAccountId, UUID academyId);

    boolean existsByUserAccountIdAndAcademyId(UUID userAccountId, UUID academyId);
}
