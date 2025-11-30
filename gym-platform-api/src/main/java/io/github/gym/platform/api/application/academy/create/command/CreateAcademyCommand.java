package io.github.gym.platform.api.application.academy.create.command;

public record CreateAcademyCommand(
    String name,
    String cnpj,
    String phone,
    String email,
    String address
) {
    public static CreateAcademyCommand with(
        final String name,
        final String cnpj,
        final String phone,
        final String email,
        final String address
    ) {
        return new CreateAcademyCommand(name, cnpj, phone, email, address);
    }
}
