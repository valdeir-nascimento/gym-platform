package io.github.gym.platform.api.application.teacher;

import io.github.gym.platform.api.domain.teacher.Teacher;
import io.github.gym.platform.api.domain.teacher.TeacherStatus;
import io.github.gym.platform.api.domain.user.UserAccount;

import java.time.Instant;

public record TeacherOutput(
    String id,
    String userAccountId,
    String academyId,
    String fullName,
    String email,
    String phone,
    String specialization,
    TeacherStatus status,
    Instant hiredAt,
    Instant createdAt,
    Instant updatedAt
) {

    public static TeacherOutput from(final Teacher teacher, final UserAccount user) {
        return new TeacherOutput(
            teacher.getId().getValue().toString(),
            teacher.getUserAccountId().getValue().toString(),
            teacher.getAcademyId().getValue().toString(),
            user.getFullName(),
            user.getEmail(),
            user.getPhone(),
            teacher.getSpecialization(),
            teacher.getStatus(),
            teacher.getHiredAt(),
            teacher.getCreatedAt(),
            teacher.getUpdatedAt()
        );
    }
}

