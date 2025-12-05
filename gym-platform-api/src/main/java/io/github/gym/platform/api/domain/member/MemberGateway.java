package io.github.gym.platform.api.domain.member;

import io.github.gym.platform.api.application.member.MemberItemOutput;
import io.github.gym.platform.api.domain.academy.AcademyID;

import java.util.List;

public interface MemberGateway {

    Member save(Member member);

    Member findById(MemberID academyId);

    List<MemberItemOutput> findMembersByAcademyId(AcademyID academyId);

    void existById(MemberID id);

}
