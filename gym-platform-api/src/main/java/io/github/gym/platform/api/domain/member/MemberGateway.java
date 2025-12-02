package io.github.gym.platform.api.domain.member;

import java.util.List;
import java.util.UUID;

public interface MemberGateway {

    Member save(Member member);

    Member findById(MemberID id);

    Member findByUserAndAcademy(UUID userAccountId, UUID academyId);

    boolean existsByUserAndAcademy(UUID userAccountId, UUID academyId);

    List<Member> findAll();
}
