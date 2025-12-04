package io.github.gym.platform.api.application.member.retrieve;

import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.retrieve.query.GetMemberByIdQuery;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import org.springframework.stereotype.Component;

@Component
public class GetMemberByIdUseCaseImpl implements GetMemberByIdUseCase {

    private final MemberGateway memberGateway;
    private final UserAccountGateway userAccountGateway;
    private final PlanGateway planGateway;

    public GetMemberByIdUseCaseImpl(
        final MemberGateway memberGateway,
        final UserAccountGateway userAccountGateway,
        final PlanGateway planGateway
    ) {
        this.memberGateway = memberGateway;
        this.userAccountGateway = userAccountGateway;
        this.planGateway = planGateway;
    }

    @Override
    public MemberOutput execute(final GetMemberByIdQuery query) {
        final var member = memberGateway.findById(MemberID.from(query.memberId()));
        final var user = userAccountGateway.findById(member.getUserAccountId());
        final var plan = planGateway.findById(member.getPlanId());
        return MemberOutput.from(member, user, plan);
    }
}

