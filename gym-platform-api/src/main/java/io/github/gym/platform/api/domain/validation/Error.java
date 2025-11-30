package io.github.gym.platform.api.domain.validation;

public record Error(String message) {
    public static Error of(String message) {
        return new Error(message);
    }
}
