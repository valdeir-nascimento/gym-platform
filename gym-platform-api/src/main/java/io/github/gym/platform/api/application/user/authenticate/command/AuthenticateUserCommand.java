package io.github.gym.platform.api.application.user.authenticate.command;

public record AuthenticateUserCommand(
    String email,
    String password
) {

    public static AuthenticateUserCommand with(final String email, final String password) {
        return new AuthenticateUserCommand(email, password);
    }
}

