package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.application.member.MemberItemOutput;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.plan.PlanGateway;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.infrastructure.persistence.entity.MemberJpaEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.MemberJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MemberGatewayImpl implements MemberGateway {

    private final MemberJpaRepository memberJpaRepository;
    private final UserAccountGateway userAccountGateway;
    private final PlanGateway planGateway;
    private final AcademyGateway academyGateway;

    public MemberGatewayImpl(
        final MemberJpaRepository memberJpaRepository,
        final UserAccountGateway userAccountGateway,
        final PlanGateway planGateway,
        final AcademyGateway academyGateway
    ) {
        this.memberJpaRepository = memberJpaRepository;
        this.userAccountGateway = userAccountGateway;
        this.planGateway = planGateway;
        this.academyGateway = academyGateway;
    }

    @Override
    @Transactional
    public Member save(Member member) {
        final var userAccount = userAccountGateway.findById(member.getUserAccountId());
        final var plan = planGateway.findById(member.getPlanId());
        final var academy = academyGateway.findById(member.getAcademyId());

        final var entity = MemberJpaEntity.from(
            member,
            userAccount,
            academy,
            plan
        );

        final var saved = memberJpaRepository.save(entity);

        return saved.toAggregate();
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(MemberID id) {
        return memberJpaRepository.findById(id.getValue())
            .map(MemberJpaEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Member.class, id.getValue()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberItemOutput> findMembersByAcademyId(AcademyID academyId) {
        return memberJpaRepository.findByAcademyId(academyId.getValue()).stream()
            .map(MemberItemOutput::from)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void existById(MemberID id) {
        if (!memberJpaRepository.existsById(id.getValue())) {
            throw NotFoundException.with(Member.class, id.getValue());
        }
    }
}
