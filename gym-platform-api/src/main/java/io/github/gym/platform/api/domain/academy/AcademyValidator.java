package io.github.gym.platform.api.domain.academy;

import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.ValidationHandler;
import io.github.gym.platform.api.domain.validation.Validator;

import java.util.regex.Pattern;

public class AcademyValidator extends Validator {

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 150;

    private static final int CNPJ_MAX_LENGTH = 18; // com máscara
    private static final int PHONE_MAX_LENGTH = 20;
    private static final int EMAIL_MAX_LENGTH = 150;
    private static final int ADDRESS_MAX_LENGTH = 255;

    // Regex simples para email
    private static final Pattern SIMPLE_EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final Academy academy;

    public AcademyValidator(final Academy academy, final ValidationHandler handler) {
        super(handler);
        this.academy = academy;
    }

    @Override
    public void validate() {
        checkId();
        checkName();
        checkCnpj();
        checkPhone();
        checkEmail();
        checkAddress();
    }

    private void checkId() {
        if (academy.getId() == null || academy.getId().getValue() == null) {
            validationHandler().append(Error.of("'id' must not be null"));
        }
    }

    private void checkName() {
        final var name = academy.getName();

        if (name == null) {
            validationHandler().append(Error.of("'name' must not be null"));
            return;
        }

        final var trimmed = name.trim();

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

    private void checkCnpj() {
        final var cnpj = academy.getCnpj();

        if (cnpj == null || cnpj.isBlank()) {
            return; // opcional no domínio
        }

        final var trimmed = cnpj.trim();

        if (trimmed.length() > CNPJ_MAX_LENGTH) {
            validationHandler().append(Error.of(
                    "'cnpj' must not be longer than %d characters".formatted(CNPJ_MAX_LENGTH)
            ));
            return;
        }

        if (!isValidCnpj(trimmed)) {
            validationHandler().append(Error.of("'cnpj' must be a valid CNPJ"));
        }
    }

    private void checkPhone() {
        final var phone = academy.getPhone();

        if (phone == null || phone.isBlank()) {
            return; // opcional
        }

        final var trimmed = phone.trim();

        if (trimmed.length() > PHONE_MAX_LENGTH) {
            validationHandler().append(Error.of(
                    "'phone' must not be longer than %d characters".formatted(PHONE_MAX_LENGTH)
            ));
        }
    }

    private void checkEmail() {
        final var email = academy.getEmail();

        if (email == null || email.isBlank()) {
            return; // opcional
        }

        final var trimmed = email.trim();

        if (trimmed.length() > EMAIL_MAX_LENGTH) {
            validationHandler().append(Error.of(
                    "'email' must not be longer than %d characters".formatted(EMAIL_MAX_LENGTH)
            ));
        }

        if (!SIMPLE_EMAIL_PATTERN.matcher(trimmed).matches()) {
            validationHandler().append(Error.of("'email' must be a valid email address"));
        }
    }

    private void checkAddress() {
        final var address = academy.getAddress();

        if (address == null || address.isBlank()) {
            return; // opcional
        }

        final var trimmed = address.trim();

        if (trimmed.length() > ADDRESS_MAX_LENGTH) {
            validationHandler().append(Error.of(
                    "'address' must not be longer than %d characters".formatted(ADDRESS_MAX_LENGTH)
            ));
        }
    }

    /**
     * Validação de CNPJ com base no algoritmo oficial (dígitos verificadores).
     */
    private boolean isValidCnpj(final String cnpjRaw) {
        // Mantém apenas dígitos
        final var digitsOnly = cnpjRaw.replaceAll("\\D", "");

        // CNPJ deve ter 14 dígitos
        if (digitsOnly.length() != 14) {
            return false;
        }

        // Rejeita sequências com todos os dígitos iguais (ex.: 000... / 111... / etc.)
        if (digitsOnly.chars().distinct().count() == 1) {
            return false;
        }

        try {
            final var digits = new int[14];
            for (int i = 0; i < 14; i++) {
                digits[i] = Character.digit(digitsOnly.charAt(i), 10);
            }

            // Cálculo do primeiro dígito verificador
            final int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += digits[i] * weight1[i];
            }
            int remainder = sum % 11;
            int checkDigit1 = (remainder < 2) ? 0 : 11 - remainder;

            if (digits[12] != checkDigit1) {
                return false;
            }

            // Cálculo do segundo dígito verificador
            final int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += digits[i] * weight2[i];
            }
            remainder = sum % 11;
            int checkDigit2 = (remainder < 2) ? 0 : 11 - remainder;

            return digits[13] == checkDigit2;
        } catch (final Exception ignored) {
            return false;
        }
    }
}
