package io.github.gym.platform.api.presentation.rest.controller.teacher.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterTeacherRequest(
    @NotBlank(message = "'fullName' is required") String fullName,
    @NotBlank(message = "'email' is required") @Email(message = "'email' must be valid") String email,
    @NotBlank(message = "'phone' is required") String phone,
    String password,
    @NotBlank(message = "'academyId' is required") String academyId,
    String specialization
) {
}

