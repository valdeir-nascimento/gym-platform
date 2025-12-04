package io.github.gym.platform.api.application.member.register.command;

import java.time.LocalDate;

public record RegisterMemberCommand(
    String fullName,
    String email,
    String phone,
    String cpf,
    LocalDate birthDate,
    String planId,
    String password
) {

    public static RegisterMemberCommand with(
        final String fullName,
        final String email,
        final String phone,
        final String cpf,
        final LocalDate birthDate,
        final String planId,
        final String password
    ) {
        return new RegisterMemberCommand(fullName, email, phone, cpf, birthDate, planId, password);
    }
}

