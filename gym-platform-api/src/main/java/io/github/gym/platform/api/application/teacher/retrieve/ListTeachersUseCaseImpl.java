package io.github.gym.platform.api.application.teacher.retrieve;

import io.github.gym.platform.api.application.teacher.TeacherOutput;
import io.github.gym.platform.api.application.teacher.retrieve.query.ListTeachersQuery;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.validation.Error;
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
        final AcademyID academyId = resolveAcademy(query.academyId());
        final var teachers = teacherGateway.findAllByAcademy(academyId);

        return teachers.stream()
            .map(this::toOutput)
            .toList();
    }

    private TeacherOutput toOutput(final Teacher teacher) {
        final UserAccount user = fetchUser(teacher.getUserAccountId());
        return TeacherOutput.from(teacher, user);
    }

    private UserAccount fetchUser(final UserAccountID userAccountID) {
        return userAccountGateway.findById(userAccountID)
            .orElseThrow(() -> DomainException.with(List.of(Error.of("User '%s' not found".formatted(userAccountID.getValue())))));
    }

    private AcademyID resolveAcademy(final String rawAcademyId) {
        if (rawAcademyId == null || rawAcademyId.isBlank()) {
            throw DomainException.with(List.of(Error.of("'academyId' must not be null or blank")));
        }

        try {
            final var academyId = AcademyID.from(rawAcademyId);
            academyGateway.findById(academyId);
            return academyId;
        } catch (final IllegalArgumentException ex) {
            throw DomainException.with(List.of(Error.of("'academyId' must be a valid UUID")));
        } catch (final NotFoundException ex) {
            throw DomainException.with(List.of(Error.of("Academy '%s' was not found".formatted(rawAcademyId))));
        }
    }
}

