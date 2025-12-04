package io.github.gym.platform.api.presentation.rest.controller.teacher.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterTeacherRequest(
    @NotBlank(message = "'fullName' is required") String fullName,
    @NotBlank(message = "'email' is required") @Email(message = "'email' must be valid") String email,
    @NotBlank(message = "'phone' is required") String phone,
    @NotBlank(message = "'cpf' is required") String cpf,
    @NotNull(message = "'birthDate' is required") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
    String password,
    @NotBlank(message = "'academyId' is required") String academyId,
    String specialization
) {
}

