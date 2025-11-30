package io.github.gym.platform.api.domain.member;

import java.util.Optional;
import java.util.UUID;

public interface MemberGateway {

    Member save(Member member);

    Optional<Member> findById(MemberID id);

    Optional<Member> findByUserAndAcademy(UUID userAccountId, UUID academyId);

    boolean existsByUserAndAcademy(UUID userAccountId, UUID academyId);
}
