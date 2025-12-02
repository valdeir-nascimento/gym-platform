package io.github.gym.platform.api.application.member.retrieve.query;

public record ListMembersQuery(String academyId) {

    public static ListMembersQuery all() {
        return new ListMembersQuery(null);
    }

    public static ListMembersQuery with(final String academyId) {
        return new ListMembersQuery(academyId);
    }
}

