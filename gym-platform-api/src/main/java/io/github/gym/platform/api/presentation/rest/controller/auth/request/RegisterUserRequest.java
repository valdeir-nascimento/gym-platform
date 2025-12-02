package io.github.gym.platform.api.presentation.rest.controller.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterUserRequest(
    @NotBlank(message = "must not be blank") String fullName,
    @NotBlank(message = "must not be blank") @Email(message = "must be a valid email") String email,
    @Size(max = 20, message = "must have at most 20 characters") String phone,
    @NotBlank(message = "must not be blank") @Size(min = 6, message = "must be at least 6 characters") String password,
    Set<String> roles
) {
}

