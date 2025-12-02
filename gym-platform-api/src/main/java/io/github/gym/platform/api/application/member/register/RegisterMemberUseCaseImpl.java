package io.github.gym.platform.api.application.member.register;

import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.register.command.RegisterMemberCommand;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.plan.Plan;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserRole;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class RegisterMemberUseCaseImpl implements RegisterMemberUseCase {

    private final MemberGateway memberGateway;
    private final UserAccountGateway userAccountGateway;
    private final PlanGateway planGateway;
    private final PasswordEncoder passwordEncoder;

    public RegisterMemberUseCaseImpl(
        final MemberGateway memberGateway,
        final UserAccountGateway userAccountGateway,
        final PlanGateway planGateway,
        final PasswordEncoder passwordEncoder
    ) {
        this.memberGateway = Objects.requireNonNull(memberGateway);
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.planGateway = Objects.requireNonNull(planGateway);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Override
    public MemberOutput execute(final RegisterMemberCommand command) {
        final var notification = Notification.create();

        final var plan = resolvePlan(command.planId(), notification);
        final var user = resolveUser(command, notification);

        if (notification.hasErrors() || plan == null || user == null) {
            throw DomainException.with(notification.getErrors());
        }

        final var academyId = plan.getAcademyId();

        if (memberGateway.existsByUserAndAcademy(user.getId().getValue(), academyId.getValue())) {
            notification.append(Error.of("Member already registered for this academy"));
        }

        final var member = Member.newMember(user.getId(), academyId, plan.getId());
        member.validate(notification);

        if (notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        final var saved = memberGateway.save(member);
        return MemberOutput.from(saved, user, plan);
    }

    private Plan resolvePlan(final String rawPlanId, final Notification notification) {
        if (rawPlanId == null || rawPlanId.isBlank()) {
            notification.append(Error.of("'planId' must not be null or blank"));
            return null;
        }

        final PlanID planId;
        try {
            planId = PlanID.from(rawPlanId);
        } catch (final IllegalArgumentException ex) {
            notification.append(Error.of("'planId' must be a valid UUID"));
            return null;
        }

        try {
            return planGateway.findById(planId);
        } catch (final NotFoundException ex) {
            notification.append(Error.of("Plan '%s' was not found".formatted(rawPlanId)));
            return null;
        }
    }

    private UserAccount resolveUser(final RegisterMemberCommand command, final Notification notification) {
        final var existing = userAccountGateway.findByEmail(command.email());
        if (existing.isPresent()) {
            return existing.get();
        }

        final var password = resolvePassword(command);

        final var account = UserAccount.newAccount(
            command.fullName(),
            command.email(),
            command.phone(),
            passwordEncoder.encode(password),
            Set.of(UserRole.MEMBER)
        );

        account.validate(notification);

        if (notification.hasErrors()) {
            return null;
        }

        return userAccountGateway.save(account);
    }

    private String resolvePassword(final RegisterMemberCommand command) {
        if (command.password() != null && !command.password().isBlank()) {
            return command.password();
        }
        if (command.phone() != null && !command.phone().isBlank()) {
            return command.phone();
        }
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}

