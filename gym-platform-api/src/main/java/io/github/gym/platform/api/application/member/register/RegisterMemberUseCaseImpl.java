package io.github.gym.platform.api.application.member.register;

import io.github.gym.platform.api.application.member.register.command.RegisterMemberCommand;
import io.github.gym.platform.api.application.plan.retrieve.GetPlanByIdUseCase;
import io.github.gym.platform.api.application.plan.retrieve.query.GetPlanByIdQuery;
import io.github.gym.platform.api.application.user.register.RegisterUserUseCase;
import io.github.gym.platform.api.application.user.register.command.RegisterUserCommand;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotificationException;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.plan.PlanID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.user.UserRole;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class RegisterMemberUseCaseImpl implements RegisterMemberUseCase {

    private final MemberGateway memberGateway;
    private final GetPlanByIdUseCase getPlanByIdUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    public RegisterMemberUseCaseImpl(
        final MemberGateway memberGateway,
        final GetPlanByIdUseCase getPlanByIdUseCase,
        final RegisterUserUseCase registerUserUseCase
    ) {
        this.memberGateway = Objects.requireNonNull(memberGateway);
        this.getPlanByIdUseCase = Objects.requireNonNull(getPlanByIdUseCase);
        this.registerUserUseCase = Objects.requireNonNull(registerUserUseCase);
    }

    @Override
    public MemberRegisterOutput execute(final RegisterMemberCommand command) {
        final var notification = Notification.create();

        final var userCommand = RegisterUserCommand.with(
            command.fullName(),
            command.email(),
            command.phone(),
            command.cpf(),
            command.birthDate(),
            command.password(),
            Set.of(UserRole.MEMBER.name())
        );

        final var userOutput = registerUserUseCase.execute(userCommand);

        final var planOutput = getPlanByIdUseCase.execute(GetPlanByIdQuery.with(command.planId()));

        final var member = Member.newMember(
            UserAccountID.from(userOutput.id()),
            AcademyID.from(planOutput.academyId()),
            PlanID.from(planOutput.id())
        );

        member.validate(notification);

        if (notification.hasErrors()) {
            throw NotificationException.with(notification.getErrors());
        }

        final var saved = memberGateway.save(member);

        return MemberRegisterOutput.from(saved, userOutput, planOutput);
    }
}

