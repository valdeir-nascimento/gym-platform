package io.github.gym.platform.api.application.member.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.retrieve.query.ListMembersQuery;

import java.util.List;

public interface ListMembersUseCase extends QueryUseCase<ListMembersQuery, List<MemberOutput>> {
}

