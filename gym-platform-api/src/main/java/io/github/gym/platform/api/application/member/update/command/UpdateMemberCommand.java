package io.github.gym.platform.api.application.member.update.command;

import java.time.LocalDate;

public record UpdateMemberCommand(
    String memberId,
    String fullName,
    String email,
    String phone,
    String cpf,
    LocalDate birthDate,
    String planId
) {

    public static UpdateMemberCommand with(
        final String memberId,
        final String fullName,
        final String email,
        final String phone,
        final String cpf,
        final LocalDate birthDate,
        final String planId
    ) {
        return new UpdateMemberCommand(memberId, fullName, email, phone, cpf, birthDate, planId);
    }
}

