package io.github.gym.platform.api.domain.exception;

import io.github.gym.platform.api.domain.validation.Error;

import java.util.List;

public class AuthenticationException extends NoStacktraceException {

    private final List<Error> errors;

    private AuthenticationException(final String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static AuthenticationException invalidCredentials() {
        final var error = Error.of("Invalid credentials");
        return new AuthenticationException("Authentication failed", List.of(error));
    }

    public List<Error> getErrors() {
        return errors;
    }
}

