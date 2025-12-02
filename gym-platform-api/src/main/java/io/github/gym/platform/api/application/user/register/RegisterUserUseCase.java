package io.github.gym.platform.api.application.user.register;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.user.UserAccountOutput;
import io.github.gym.platform.api.application.user.register.command.RegisterUserCommand;

public interface RegisterUserUseCase extends CommandUseCase<RegisterUserCommand, UserAccountOutput> {
}

