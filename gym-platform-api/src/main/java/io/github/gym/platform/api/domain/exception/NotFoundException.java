package io.github.gym.platform.api.domain.exception;

import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.core.Identifier;
import io.github.gym.platform.api.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String message, final List<Error> errors) {
        super(message, errors);
    }

    public static NotFoundException with(
        final Class<? extends AggregateRoot<?>> aggregate,
        final Identifier<?> id
    ) {
        final var error = "%s with ID %s was not found".formatted(aggregate.getSimpleName(), id.getValue());
        return new NotFoundException(error, Collections.emptyList());
    }

    public static NotFoundException with(final Class<?> entity, final Object id) {
        final var error = "%s with ID %s was not found".formatted(
            entity.getSimpleName(),
            id
        );
        return new NotFoundException(error, Collections.emptyList());
    }
}
