package io.github.gym.platform.api.presentation.rest.error;

import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.validation.Error;

import java.util.List;

public record ApiError(List<Error> errors) {

    static ApiError from(final DomainException ex) {
        return new ApiError(ex.getErrors());
    }

    static ApiError from(final NotFoundException ex) {
        return new ApiError(List.of(Error.of(ex.getMessage())));
    }
}