package io.github.gym.platform.api.application.teacher.register.command;

import java.time.LocalDate;

public record RegisterTeacherCommand(
    String fullName,
    String email,
    String phone,
    String cpf,
    LocalDate birthDate,
    String password,
    String academyId,
    String specialization
) {

    public static RegisterTeacherCommand with(
        final String fullName,
        final String email,
        final String phone,
        final String cpf,
        final LocalDate birthDate,
        final String password,
        final String academyId,
        final String specialization
    ) {
        return new RegisterTeacherCommand(fullName, email, phone, cpf, birthDate, password, academyId, specialization);
    }
}

