package io.github.gym.platform.api.infrastructure.persistence.repository;

import io.github.gym.platform.api.infrastructure.persistence.entity.TeacherJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherJpaRepository extends JpaRepository<TeacherJpaEntity, UUID> {

    Optional<TeacherJpaEntity> findByUserAccountIdAndAcademyId(UUID userAccountId, UUID academyId);

    boolean existsByUserAccountIdAndAcademyId(UUID userAccountId, UUID academyId);

    List<TeacherJpaEntity> findAllByAcademyId(UUID academyId);
}
