package io.github.gym.platform.api.application.user.register.command;

import java.time.LocalDate;
import java.util.Set;

public record RegisterUserCommand(
    String fullName,
    String email,
    String phone,
    String cpf,
    LocalDate birthDate,
    String password,
    Set<String> roles
) {

    public static RegisterUserCommand with(
        final String fullName,
        final String email,
        final String phone,
        final String cpf,
        final LocalDate birthDate,
        final String password,
        final Set<String> roles
    ) {
        return new RegisterUserCommand(fullName, email, phone, cpf, birthDate, password, roles);
    }
}

