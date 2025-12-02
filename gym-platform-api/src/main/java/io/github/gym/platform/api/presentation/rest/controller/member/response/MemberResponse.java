package io.github.gym.platform.api.presentation.rest.controller.member.response;

import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.domain.member.MemberStatus;

public record MemberResponse(
    String id,
    String name,
    String plan,
    String status,
    String lastPayment
) {

    public static MemberResponse from(final MemberOutput output) {
        return new MemberResponse(
            output.id(),
            output.fullName(),
            output.planName(),
            toDisplayStatus(output.status()),
            output.joinedAt() != null ? output.joinedAt().toString() : null
        );
    }

    private static String toDisplayStatus(final MemberStatus status) {
        return switch (status) {
            case ACTIVE -> "ATIVO";
            case DELINQUENT -> "INADIMPLENTE";
            case CANCELED -> "CANCELADO";
        };
    }
}

