package io.github.gym.platform.api.presentation.rest.controller.member.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UpdateMemberRequest(
    @NotBlank(message = "'fullName' is required") String fullName,
    @NotBlank(message = "'email' is required") @Email(message = "'email' must be valid") String email,
    @NotBlank(message = "'phone' is required") String phone,
    @CPF @NotBlank(message = "'cpf' is required") String cpf,
    @NotNull(message = "'birthDate' is required") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
    @NotBlank(message = "'planId' is required") String planId
) {
}

