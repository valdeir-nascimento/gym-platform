package io.github.gym.platform.api.application.member.retrieve.query;

public record GetMemberByIdQuery(String memberId) {

    public static GetMemberByIdQuery with(final String memberId) {
        return new GetMemberByIdQuery(memberId);
    }
}

