package io.github.gym.platform.api.presentation.rest.error;

import io.github.gym.platform.api.domain.exception.AuthenticationException;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.validation.Error;
import jakarta.persistence.OptimisticLockException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(final DomainException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(final AuthenticationException ex) {
        final ApiError apiError = new ApiError(
            "Authentication failed",
            ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        final List<Error> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(GlobalExceptionHandler::toDomainError)
            .toList();
        final ApiError apiError = new ApiError("Validation Error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiError> handleOptimisticLock(OptimisticLockException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ApiError("Concurrency error, try again",
                List.of(Error.of(ex.getMessage()))));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(final DataIntegrityViolationException ex) {
        final String message = resolveConstraintMessage(ex);
        final ApiError apiError = new ApiError(
            message,
            List.of(Error.of(message))
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        final var message = String.format("Required request parameter '%s' is missing", ex.getParameterName());
        final var apiError = new ApiError("Missing Request Parameter", List.of(Error.of(message)));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    private String resolveConstraintMessage(final DataIntegrityViolationException ex) {
        final Throwable rootCause = NestedExceptionUtils.getMostSpecificCause(ex);
        final String detailedMessage = rootCause != null && rootCause.getMessage() != null
            ? rootCause.getMessage()
            : ex.getMessage();

        if (detailedMessage == null) {
            return "Unique constraint violated";
        }

        if (detailedMessage.contains("user_account_phone_key")) {
            return "'phone' is already in use";
        }
        if (detailedMessage.contains("user_account_email_key") || detailedMessage.contains("ux_user_account_email")) {
            return "'email' is already in use";
        }
        if (detailedMessage.contains("ux_academy_phone")) {
            return "'phone' is already in use for academy";
        }
        if (detailedMessage.contains("ux_academy_cnpj")) {
            return "'cnpj' is already in use";
        }
        return "Unique constraint violated";
    }

    private static Error toDomainError(FieldError fieldError) {
        final String message = String.format("'%s' %s",
            fieldError.getField(),
            fieldError.getDefaultMessage()
        );
        return new Error(message);
    }
}
