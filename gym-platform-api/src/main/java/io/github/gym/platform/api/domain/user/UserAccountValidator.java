package io.github.gym.platform.api.domain.user;

import io.github.gym.platform.api.domain.validation.ValidationHandler;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.Validator;

public class UserAccountValidator extends Validator {

    private final UserAccount userAccount;

    public UserAccountValidator(final UserAccount userAccount, final ValidationHandler handler) {
        super(handler);
        this.userAccount = userAccount;
    }

    @Override
    public void validate() {
        if (userAccount.getFullName() == null || userAccount.getFullName().isBlank()) {
            validationHandler().append(io.github.gym.platform.api.domain.validation.Error.of("'username' must not be null or blank"));
        }

        if (userAccount.getCpf() == null || userAccount.getCpf().isBlank()) {
            validationHandler().append(Error.of("'cpf' must not be null or blank"));
        }

        if (userAccount.getBirthDate() == null) {
            validationHandler().append(Error.of("'birthDate' must not be null"));
        }

        if (userAccount.getPassword() == null || userAccount.getPassword().isBlank()) {
            validationHandler().append(Error.of("'password' must not be null or blank"));
        }

        if (userAccount.getRoles() == null || userAccount.getRoles().isEmpty()) {
            validationHandler().append(Error.of("'roles' must contain at least one role"));
        }
    }
}

