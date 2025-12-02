package io.github.gym.platform.api.application.teacher.register;

import io.github.gym.platform.api.application.teacher.TeacherOutput;
import io.github.gym.platform.api.application.teacher.register.command.RegisterTeacherCommand;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserRole;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class RegisterTeacherUseCaseImpl implements RegisterTeacherUseCase {

    private final TeacherGateway teacherGateway;
    private final UserAccountGateway userAccountGateway;
    private final AcademyGateway academyGateway;
    private final PasswordEncoder passwordEncoder;

    public RegisterTeacherUseCaseImpl(
        final TeacherGateway teacherGateway,
        final UserAccountGateway userAccountGateway,
        final AcademyGateway academyGateway,
        final PasswordEncoder passwordEncoder
    ) {
        this.teacherGateway = Objects.requireNonNull(teacherGateway);
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.academyGateway = Objects.requireNonNull(academyGateway);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Override
    public TeacherOutput execute(final RegisterTeacherCommand command) {
        final var notification = Notification.create();

        final AcademyID academyId = resolveAcademy(command.academyId(), notification);
        final UserAccount user = resolveUser(command, notification);

        if (academyId == null || user == null || notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        if (teacherGateway.existsByUserAndAcademy(user.getId(), academyId)) {
            notification.append(Error.of("Teacher already registered for this academy"));
            throw DomainException.with(notification.getErrors());
        }

        final var specialization = normalizeSpecialization(command.specialization());
        final var teacher = Teacher.newTeacher(user.getId(), academyId, specialization);
        teacher.validate(notification);

        if (notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        final var saved = teacherGateway.save(teacher);
        return TeacherOutput.from(saved, user);
    }

    private AcademyID resolveAcademy(final String rawAcademyId, final Notification notification) {
        if (rawAcademyId == null || rawAcademyId.isBlank()) {
            notification.append(Error.of("'academyId' must not be null or blank"));
            return null;
        }

        final AcademyID academyId;
        try {
            academyId = AcademyID.from(rawAcademyId);
        } catch (final IllegalArgumentException ex) {
            notification.append(Error.of("'academyId' must be a valid UUID"));
            return null;
        }

        try {
            academyGateway.findById(academyId);
            return academyId;
        } catch (final NotFoundException ex) {
            notification.append(Error.of("Academy '%s' was not found".formatted(rawAcademyId)));
            return null;
        }
    }

    private UserAccount resolveUser(final RegisterTeacherCommand command, final Notification notification) {
        final var existing = userAccountGateway.findByEmail(command.email());
        if (existing.isPresent()) {
            return existing.get();
        }

        final String password = resolvePassword(command);
        final var account = UserAccount.newAccount(
            command.fullName(),
            command.email(),
            command.phone(),
            passwordEncoder.encode(password),
            Set.of(UserRole.TEACHER)
        );

        account.validate(notification);
        if (notification.hasErrors()) {
            return null;
        }

        return userAccountGateway.save(account);
    }

    private String normalizeSpecialization(final String specialization) {
        if (specialization == null) {
            return null;
        }
        final String trimmed = specialization.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String resolvePassword(final RegisterTeacherCommand command) {
        if (command.password() != null && !command.password().isBlank()) {
            return command.password();
        }
        if (command.phone() != null && !command.phone().isBlank()) {
            return command.phone();
        }
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}

