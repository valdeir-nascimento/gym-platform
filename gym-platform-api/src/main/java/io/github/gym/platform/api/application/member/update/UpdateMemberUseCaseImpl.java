package io.github.gym.platform.api.application.member.update;

import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.update.command.UpdateMemberCommand;
import io.github.gym.platform.api.domain.exception.NotificationException;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UpdateMemberUseCaseImpl implements UpdateMemberUseCase {

    private final MemberGateway memberGateway;
    private final UserAccountGateway userAccountGateway;
    private final PlanGateway planGateway;

    public UpdateMemberUseCaseImpl(
        final MemberGateway memberGateway,
        final UserAccountGateway userAccountGateway,
        final PlanGateway planGateway
    ) {
        this.memberGateway = Objects.requireNonNull(memberGateway);
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.planGateway = Objects.requireNonNull(planGateway);
    }

    @Override
    public MemberOutput execute(final UpdateMemberCommand command) {
        final var memberId = MemberID.from(command.memberId());
        final var planId = PlanID.from(command.planId());

        final var member = memberGateway.findById(memberId);

        final var userId = member.getUserAccountId();
        final var userAccount = userAccountGateway.findById(userId);

        final var plan = planGateway.findById(planId);

        final var notification = Notification.create();
        final var updatedUser = userAccount.updateProfile(
            command.fullName(),
            command.email(),
            command.phone(),
            command.cpf(),
            command.birthDate()
        );

        updatedUser.validate(notification);

        if (notification.hasErrors()) {
            throw new NotificationException("UserAccount data is invalid", notification);
        }

        final var updatedMember = member.update(
            userId,
            planId,
            member.getStatus()
        );

        updatedMember.validate(notification);

        if (notification.hasErrors()) {
            throw new NotificationException("Member data is invalid", notification);
        }

        final var savedUser = userAccountGateway.save(updatedUser);

        final var savedMember = memberGateway.save(updatedMember);

        return MemberOutput.from(savedMember, savedUser, plan);
    }
}

