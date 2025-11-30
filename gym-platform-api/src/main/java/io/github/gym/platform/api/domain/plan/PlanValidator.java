package io.github.gym.platform.api.domain.plan;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.ValidationHandler;
import io.github.gym.platform.api.domain.validation.Validator;

import java.math.BigDecimal;

public class PlanValidator extends Validator {

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 100;
    private static final int DESCRIPTION_MAX_LENGTH = 255;

    private final Plan plan;

    public PlanValidator(final Plan plan, final ValidationHandler handler) {
        super(handler);
        this.plan = plan;
    }

    @Override
    public void validate() {
        checkId();
        checkAcademyId();
        checkName();
        checkPrice();
        checkBillingPeriodInMonths();
        checkDescription();
    }

    private void checkId() {
        if (plan.getId() == null || plan.getId().getValue() == null) {
            validationHandler().append(Error.of("'id' must not be null"));
        }
    }

    private void checkAcademyId() {
        final AcademyID academyId = plan.getAcademyId();

        if (academyId == null || academyId.getValue() == null) {
            validationHandler().append(Error.of("'academyId' must not be null"));
        }
    }

    private void checkName() {
        final String name = plan.getName();

        if (name == null) {
            validationHandler().append(Error.of("'name' must not be null"));
            return;
        }

        final String trimmed = name.trim();

        if (trimmed.isEmpty()) {
            validationHandler().append(Error.of("'name' must not be blank"));
            return;
        }

        final int length = trimmed.length();
        if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            validationHandler().append(Error.of(
                    "'name' must be between %d and %d characters"
                            .formatted(NAME_MIN_LENGTH, NAME_MAX_LENGTH)
            ));
        }
    }

    private void checkPrice() {
        final BigDecimal price = plan.getPrice();

        if (price == null) {
            validationHandler().append(Error.of("'price' must not be null"));
            return;
        }

        if (price.signum() <= 0) {
            validationHandler().append(Error.of("'price' must be greater than zero"));
        }
    }

    private void checkBillingPeriodInMonths() {
        final int period = plan.getBillingPeriodInMonths();

        if (period <= 0) {
            validationHandler().append(Error.of("'billingPeriodInMonths' must be greater than zero"));
        }
    }

    private void checkDescription() {
        final String description = plan.getDescription();

        if (description == null || description.isBlank()) {
            return; // opcional
        }

        final String trimmed = description.trim();

        if (trimmed.length() > DESCRIPTION_MAX_LENGTH) {
            validationHandler().append(Error.of(
                    "'description' must not be longer than %d characters"
                            .formatted(DESCRIPTION_MAX_LENGTH)
            ));
        }
    }
}
