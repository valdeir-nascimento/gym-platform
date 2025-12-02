package io.github.gym.platform.api.application.member.register.command;

public record RegisterMemberCommand(
    String fullName,
    String email,
    String phone,
    String planId,
    String password
) {

    public static RegisterMemberCommand with(
        final String fullName,
        final String email,
        final String phone,
        final String planId,
        final String password
    ) {
        return new RegisterMemberCommand(fullName, email, phone, planId, password);
    }
}

