package io.github.gym.platform.api.presentation.rest.controller.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterMemberRequest(
    @NotBlank(message = "'fullName' is required") String fullName,
    @NotBlank(message = "'email' is required") @Email(message = "'email' must be valid") String email,
    @NotBlank(message = "'phone' is required") String phone,
    @NotBlank(message = "'planId' is required") String planId,
    String password
) {
}

