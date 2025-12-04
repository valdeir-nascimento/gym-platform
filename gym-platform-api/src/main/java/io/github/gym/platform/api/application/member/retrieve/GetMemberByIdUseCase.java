package io.github.gym.platform.api.application.member.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.retrieve.query.GetMemberByIdQuery;

public interface GetMemberByIdUseCase extends QueryUseCase<GetMemberByIdQuery, MemberOutput> {
}

