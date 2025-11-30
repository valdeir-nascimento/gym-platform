package io.github.gym.platform.api.application;

public interface QueryUseCase<Q, R> {
    R execute(Q query);
}
