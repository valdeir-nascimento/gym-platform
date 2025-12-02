package io.github.gym.platform.api.application.member;

import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberStatus;
import io.github.gym.platform.api.domain.plan.Plan;
import io.github.gym.platform.api.domain.user.UserAccount;

import java.time.Instant;

public record MemberOutput(
    String id,
    String userAccountId,
    String academyId,
    String planId,
    String fullName,
    String email,
    String phone,
    String planName,
    MemberStatus status,
    Instant joinedAt,
    Instant createdAt,
    Instant updatedAt
) {

    public static MemberOutput from(final Member member, final UserAccount user, final Plan plan) {
        return new MemberOutput(
            member.getId().getValue().toString(),
            member.getUserAccountId().getValue().toString(),
            member.getAcademyId().getValue().toString(),
            member.getPlanId().getValue().toString(),
            user.getFullName(),
            user.getEmail(),
            user.getPhone(),
            plan.getName(),
            member.getStatus(),
            member.getJoinedAt(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
}

