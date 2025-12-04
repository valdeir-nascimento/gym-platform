package io.github.gym.platform.api.application.member.register;

import io.github.gym.platform.api.application.plan.PlanOutput;
import io.github.gym.platform.api.application.user.UserAccountOutput;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberStatus;

import java.time.Instant;
import java.time.LocalDate;

public record MemberRegisterOutput(
    String id,
    String academyId,
    String fullName,
    String email,
    String phone,
    String cpf,
    LocalDate birthDate,
    MemberStatus status,
    Instant joinedAt,
    Instant createdAt,
    Instant updatedAt,
    PlanOutput plan
) {
    public static MemberRegisterOutput from(
        final Member member,
        final UserAccountOutput user,
        final PlanOutput plan
    ) {
        return new MemberRegisterOutput(
            member.getId().getValue().toString(),
            member.getAcademyId().getValue().toString(),
            user.fullName(),
            user.email(),
            user.phone(),
            user.cpf(),
            user.birthDate(),
            member.getStatus(),
            member.getJoinedAt(),
            member.getCreatedAt(),
            member.getUpdatedAt(),
            plan
        );
    }
}
