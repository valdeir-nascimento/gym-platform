package io.github.gym.platform.api.application.member.retrieve;

import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.retrieve.query.ListMembersQuery;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.validation.Error;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ListMembersUseCaseImpl implements ListMembersUseCase {

    private final MemberGateway memberGateway;
    private final UserAccountGateway userAccountGateway;
    private final PlanGateway planGateway;

    public ListMembersUseCaseImpl(
        final MemberGateway memberGateway,
        final UserAccountGateway userAccountGateway,
        final PlanGateway planGateway
    ) {
        this.memberGateway = Objects.requireNonNull(memberGateway);
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.planGateway = Objects.requireNonNull(planGateway);
    }

    @Override
    public List<MemberOutput> execute(final ListMembersQuery query) {
        final UUID academyFilter = parseAcademyId(query.academyId());

        final var members = memberGateway.findAll();

        return members.stream()
            .filter(member -> matchesAcademy(member, academyFilter))
            .map(this::toOutput)
            .toList();
    }

    private UUID parseAcademyId(final String rawAcademyId) {
        if (rawAcademyId == null || rawAcademyId.isBlank()) {
            return null;
        }

        try {
            return UUID.fromString(rawAcademyId);
        } catch (final IllegalArgumentException ex) {
            throw DomainException.with(List.of(Error.of("'academyId' must be a valid UUID")));
        }
    }

    private boolean matchesAcademy(final Member member, final UUID academyFilter) {
        if (academyFilter == null) {
            return true;
        }
        return member.getAcademyId().getValue().equals(academyFilter);
    }

    private MemberOutput toOutput(final Member member) {
        final var user = fetchUser(member.getUserAccountId());
        final var plan = planGateway.findById(member.getPlanId());
        return MemberOutput.from(member, user, plan);
    }

    private UserAccount fetchUser(final UserAccountID id) {
        return userAccountGateway.findById(id)
            .orElseThrow(() -> DomainException.with(List.of(Error.of("User '%s' not found".formatted(id.getValue())))));
    }
}

