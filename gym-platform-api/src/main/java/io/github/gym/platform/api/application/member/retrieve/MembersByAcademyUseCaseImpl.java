package io.github.gym.platform.api.application.member.retrieve;

import io.github.gym.platform.api.application.member.MemberItemOutput;
import io.github.gym.platform.api.application.member.retrieve.query.ListMembersQuery;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.member.MemberGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MembersByAcademyUseCaseImpl implements MembersByAcademyUseCase {

    private final MemberGateway memberGateway;

    public MembersByAcademyUseCaseImpl(final MemberGateway memberGateway) {
        this.memberGateway = Objects.requireNonNull(memberGateway);
    }

    @Override
    public List<MemberItemOutput> execute(final ListMembersQuery query) {
        final var academyId = AcademyID.from(query.academyId());
        return memberGateway.findMembersByAcademyId(academyId);
    }

}

