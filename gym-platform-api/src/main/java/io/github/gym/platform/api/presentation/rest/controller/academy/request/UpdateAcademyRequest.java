package io.github.gym.platform.api.presentation.rest.controller.academy.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAcademyRequest(

    @NotBlank
    @Size(min = 3, max = 150)
    String name,

    @Size(max = 18)
    String cnpj,

    @Size(max = 20)
    String phone,

    @Size(max = 150)
    @Email(message = "must be a valid email")
    String email,

    @Size(max = 255)
    String address,

    boolean active
) {
}
