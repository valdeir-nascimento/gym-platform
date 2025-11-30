package io.github.gym.platform.api.application.academy.update.command;

public record UpdateAcademyCommand(
    String id,
    String name,
    String cnpj,
    String phone,
    String email,
    String address,
    boolean active
) {
    public static UpdateAcademyCommand with(
        final String id,
        final String name,
        final String cnpj,
        final String phone,
        final String email,
        final String address,
        final boolean active
    ) {
        return new UpdateAcademyCommand(id, name, cnpj, phone, email, address, active);
    }
}
