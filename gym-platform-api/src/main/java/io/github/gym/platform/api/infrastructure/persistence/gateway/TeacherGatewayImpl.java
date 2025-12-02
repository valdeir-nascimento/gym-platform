package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.infrastructure.persistence.entity.TeacherJpaEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.TeacherJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherGatewayImpl implements TeacherGateway {

    private final TeacherJpaRepository teacherRepository;

    public TeacherGatewayImpl(final TeacherJpaRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher save(final Teacher teacher) {
        final var entity = TeacherJpaEntity.from(teacher);
        final var saved = teacherRepository.save(entity);
        return saved.toAggregate();
    }

    @Override
    public Teacher findById(final TeacherID id) {
        return teacherRepository.findById(id.getValue())
            .map(TeacherJpaEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Teacher.class, id.getValue()));
    }

    @Override
    public Teacher findByUserAndAcademy(final UserAccountID userAccountId, final AcademyID academyId) {
        return teacherRepository.findByUserAccountIdAndAcademyId(userAccountId.getValue(), academyId.getValue())
            .map(TeacherJpaEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Teacher.class, userAccountId.getValue()));
    }

    @Override
    public boolean existsByUserAndAcademy(final UserAccountID userAccountId, final AcademyID academyId) {
        return teacherRepository.existsByUserAccountIdAndAcademyId(
            userAccountId.getValue(),
            academyId.getValue()
        );
    }

    @Override
    public List<Teacher> findAllByAcademy(final AcademyID academyId) {
        return teacherRepository.findAllByAcademyId(academyId.getValue())
            .stream()
            .map(TeacherJpaEntity::toAggregate)
            .toList();
    }
}
