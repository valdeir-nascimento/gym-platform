package io.github.gym.platform.api.domain.teacher;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.ValidationHandler;
import io.github.gym.platform.api.domain.validation.Validator;

import java.time.Instant;

public class TeacherValidator extends Validator {

    private static final int SPECIALIZATION_MAX_LENGTH = 150;

    private final Teacher teacher;

    public TeacherValidator(final Teacher teacher, final ValidationHandler handler) {
        super(handler);
        this.teacher = teacher;
    }

    @Override
    public void validate() {
        checkId();
        checkUserAccountId();
        checkAcademyId();
        checkStatus();
        checkHiredAt();
        checkSpecialization();
    }

    private void checkId() {
        if (teacher.getId() == null || teacher.getId().getValue() == null) {
            validationHandler().append(Error.of("'id' must not be null"));
        }
    }

    private void checkUserAccountId() {
        final UserAccountID userAccountId = teacher.getUserAccountId();

        if (userAccountId == null || userAccountId.getValue() == null) {
            validationHandler().append(Error.of("'userAccountId' must not be null"));
        }
    }

    private void checkAcademyId() {
        final AcademyID academyId = teacher.getAcademyId();

        if (academyId == null || academyId.getValue() == null) {
            validationHandler().append(Error.of("'academyId' must not be null"));
        }
    }

    private void checkStatus() {
        if (teacher.getStatus() == null) {
            validationHandler().append(Error.of("'status' must not be null"));
        }
    }

    private void checkHiredAt() {
        final Instant hiredAt = teacher.getHiredAt();

        if (hiredAt == null) {
            validationHandler().append(Error.of("'hiredAt' must not be null"));
        }
    }

    private void checkSpecialization() {
        final String specialization = teacher.getSpecialization();

        if (specialization == null) {
            return;
        }

        final String trimmed = specialization.trim();

        if (trimmed.isEmpty()) {
            validationHandler().append(Error.of("'specialization' must not be blank when provided"));
            return;
        }

        if (trimmed.length() > SPECIALIZATION_MAX_LENGTH) {
            validationHandler().append(Error.of(
                "'specialization' must not be longer than %d characters"
                    .formatted(SPECIALIZATION_MAX_LENGTH)
            ));
        }
    }
}
