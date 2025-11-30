package io.github.gym.platform.api.domain.member;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.ValidationHandler;
import io.github.gym.platform.api.domain.validation.Validator;

import java.time.Instant;

public class MemberValidator extends Validator {

    private final Member member;

    public MemberValidator(final Member member, final ValidationHandler handler) {
        super(handler);
        this.member = member;
    }

    @Override
    public void validate() {
        checkId();
        checkUserAccountId();
        checkAcademyId();
        checkStatus();
        checkJoinedAt();
    }

    private void checkId() {
        if (member.getId() == null || member.getId().getValue() == null) {
            validationHandler().append(Error.of("'id' must not be null"));
        }
    }

    private void checkUserAccountId() {
        final UserAccountID userAccountId = member.getUserAccountId();

        if (userAccountId == null || userAccountId.getValue() == null) {
            validationHandler().append(Error.of("'userAccountId' must not be null"));
        }
    }

    private void checkAcademyId() {
        final AcademyID academyId = member.getAcademyId();

        if (academyId == null || academyId.getValue() == null) {
            validationHandler().append(Error.of("'academyId' must not be null"));
        }
    }

    private void checkStatus() {
        if (member.getStatus() == null) {
            validationHandler().append(Error.of("'status' must not be null"));
        }
    }

    private void checkJoinedAt() {
        final Instant joinedAt = member.getJoinedAt();

        if (joinedAt == null) {
            validationHandler().append(Error.of("'joinedAt' must not be null"));
        }
    }
}
