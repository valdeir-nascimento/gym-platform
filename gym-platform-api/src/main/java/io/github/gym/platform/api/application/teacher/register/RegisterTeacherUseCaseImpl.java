package io.github.gym.platform.api.application.teacher.register;

import io.github.gym.platform.api.application.teacher.register.command.RegisterTeacherCommand;
import io.github.gym.platform.api.application.user.register.RegisterUserUseCase;
import io.github.gym.platform.api.application.user.register.command.RegisterUserCommand;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherGateway;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.user.UserRole;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class RegisterTeacherUseCaseImpl implements RegisterTeacherUseCase {

    private final TeacherGateway teacherGateway;
    private final AcademyGateway academyGateway;
    private final RegisterUserUseCase registerUserUseCase;

    public RegisterTeacherUseCaseImpl(
        final TeacherGateway teacherGateway,
        final AcademyGateway academyGateway,
        final RegisterUserUseCase registerUserUseCase
    ) {
        this.teacherGateway = Objects.requireNonNull(teacherGateway);
        this.academyGateway = Objects.requireNonNull(academyGateway);
        this.registerUserUseCase = Objects.requireNonNull(registerUserUseCase);
    }

    @Override
    public RegisterTeacherOutput execute(final RegisterTeacherCommand command) {
        final var notification = Notification.create();

        final var userCommand = RegisterUserCommand.with(
            command.fullName(),
            command.email(),
            command.phone(),
            command.cpf(),
            command.birthDate(),
            command.password(),
            Set.of(UserRole.MEMBER.name())
        );

        final var userOutput = registerUserUseCase.execute(userCommand);

        final var academy = academyGateway.findById(AcademyID.from(command.academyId()));

        final var teacher = Teacher.newTeacher(
            UserAccountID.from(userOutput.id()),
            academy.getId(),
            command.specialization()
        );

        teacher.validate(notification);

        if (notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        final var saved = teacherGateway.save(teacher);

        return RegisterTeacherOutput.from(saved, userOutput, academy);
    }

}

