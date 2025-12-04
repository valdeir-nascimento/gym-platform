package io.github.gym.platform.api.application.member;

import io.github.gym.platform.api.infrastructure.persistence.entity.MemberJpaEntity;

import java.time.Instant;

public record MemberItemOutput(
    String memberId,
    String status,
    Instant joinedAt,
    String userId,
    String fullName,
    String userEmail,
    String academyId,
    String academyName,
    String planId,
    String planName
) {
    public static MemberItemOutput from(final MemberJpaEntity entity) {
        final var user = entity.getUserAccount();
        final var academy = entity.getAcademy();
        final var plan = entity.getPlan();
        return new MemberItemOutput(
            entity.getId().toString(),
            entity.getStatus().name(),
            entity.getJoinedAt(),
            user.getId().toString(),
            user.getFullName(),
            user.getEmail(),
            academy.getId().toString(),
            academy.getName(),
            plan.getId().toString(),
            plan.getName()
        );
    }
}