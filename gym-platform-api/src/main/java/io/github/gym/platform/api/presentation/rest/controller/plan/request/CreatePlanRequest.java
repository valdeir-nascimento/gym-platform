package io.github.gym.platform.api.presentation.rest.controller.plan.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePlanRequest(
    @NotBlank(message = "'academyId' must not be blank")
    String academyId,

    @NotBlank(message = "'name' must not be blank")
    String name,

    String description,

    @NotNull(message = "'price' must not be null")
    @DecimalMin(value = "0.01", message = "'price' must be greater than zero")
    BigDecimal price,

    @Min(value = 1, message = "'billingPeriodInMonths' must be greater than zero")
    int billingPeriodInMonths
) {
}





