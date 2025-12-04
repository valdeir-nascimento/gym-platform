package io.github.gym.platform.api.application.teacher.retrieve;

import io.github.gym.platform.api.application.teacher.TeacherOutput;
import io.github.gym.platform.api.application.teacher.retrieve.query.ListTeachersQuery;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ListTeachersUseCaseImpl implements ListTeachersUseCase {

    private final TeacherGateway teacherGateway;
    private final UserAccountGateway userAccountGateway;
    private final AcademyGateway academyGateway;

    public ListTeachersUseCaseImpl(
        final TeacherGateway teacherGateway,
        final UserAccountGateway userAccountGateway,
        final AcademyGateway academyGateway
    ) {
        this.teacherGateway = Objects.requireNonNull(teacherGateway);
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.academyGateway = Objects.requireNonNull(academyGateway);
    }

    @Override
    public List<TeacherOutput> execute(final ListTeachersQuery query) {
        final var academy = academyGateway.findById(AcademyID.from(query.academyId()));
        final var teachers = teacherGateway.findAllByAcademy(academy.getId());
        return teachers.stream()
            .map(this::toOutput)
            .toList();
    }

    private TeacherOutput toOutput(final Teacher teacher) {
        final var user = userAccountGateway.findById(teacher.getUserAccountId());
        return TeacherOutput.from(teacher, user);
    }
}

