package io.github.gym.platform.api.application;

public interface CommandUseCase<C, R> {
    R execute(C command);
}
