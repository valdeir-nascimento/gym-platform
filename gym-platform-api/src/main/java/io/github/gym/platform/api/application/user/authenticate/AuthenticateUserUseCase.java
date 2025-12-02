package io.github.gym.platform.api.application.user.authenticate;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.user.authenticate.command.AuthenticateUserCommand;

public interface AuthenticateUserUseCase extends CommandUseCase<AuthenticateUserCommand, AuthenticationTokenOutput> {
}

