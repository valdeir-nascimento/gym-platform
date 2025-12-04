package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.teacher.TeacherID;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.infrastructure.persistence.entity.TeacherJpaEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.TeacherJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherGatewayImpl implements TeacherGateway {

    private final TeacherJpaRepository teacherRepository;
    private final UserAccountGateway userAccountGateway;
    private final AcademyGateway academyGateway;

    public TeacherGatewayImpl(
        final TeacherJpaRepository teacherRepository,
        final UserAccountGateway userAccountGateway,
        final AcademyGateway academyGateway
    ) {
        this.teacherRepository = teacherRepository;
        this.userAccountGateway = userAccountGateway;
        this.academyGateway = academyGateway;
    }

    @Override
    public Teacher save(final Teacher teacher) {
        final var userAccount = userAccountGateway.findById(teacher.getUserAccountId());
        final var academy = academyGateway.findById(teacher.getAcademyId());
        final var entity = TeacherJpaEntity.from(teacher, userAccount, academy);
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
    public List<Teacher> findAllByAcademy(final AcademyID academyId) {
        return teacherRepository.findAllByAcademyId(academyId.getValue()).stream()
            .map(TeacherJpaEntity::toAggregate)
            .toList();
    }
}
