package io.github.gym.platform.api.application.member.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.member.MemberItemOutput;
import io.github.gym.platform.api.application.member.retrieve.query.ListMembersQuery;

import java.util.List;

public interface MembersByAcademyUseCase extends QueryUseCase<ListMembersQuery, List<MemberItemOutput>> {
}

