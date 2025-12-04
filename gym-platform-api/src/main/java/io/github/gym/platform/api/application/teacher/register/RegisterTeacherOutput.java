package io.github.gym.platform.api.application.teacher.register;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.user.UserAccountOutput;
import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherStatus;

import java.time.Instant;

public record RegisterTeacherOutput(
    String id,
    String fullName,
    String email,
    String phone,
    String specialization,
    TeacherStatus status,
    AcademyOutput academy,
    Instant hiredAt,
    Instant createdAt,
    Instant updatedAt
) {
    public static RegisterTeacherOutput from(
        final Teacher teacher,
        final UserAccountOutput userAccount,
        final Academy academy
    ) {
        return new RegisterTeacherOutput(
            teacher.getId().getValue().toString(),
            userAccount.fullName(),
            userAccount.email(),
            userAccount.phone(),
            teacher.getSpecialization(),
            teacher.getStatus(),
            AcademyOutput.from(academy),
            teacher.getHiredAt(),
            teacher.getCreatedAt(),
            teacher.getUpdatedAt()
        );
    }
}
