package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.member.Member;
import io.github.gym.platform.api.domain.member.MemberGateway;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.infrastructure.persistence.entity.MemberJpaEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.MemberJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MemberGatewayImpl implements MemberGateway {

    private final MemberJpaRepository memberJpaRepository;

    public MemberGatewayImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Member save(final Member member) {
        final var entity = MemberJpaEntity.from(member);
        final var saved = memberJpaRepository.save(entity);
        return saved.toAggregate();
    }

    @Override
    public Member findById(final MemberID id) {
        return memberJpaRepository.findById(id.getValue())
            .map(MemberJpaEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Member.class, id.getValue()));
    }

    @Override
    public Member findByUserAndAcademy(final UUID userAccountId, final UUID academyId) {
        return memberJpaRepository.findByUserAccountIdAndAcademyId(userAccountId, academyId)
            .map(MemberJpaEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(Member.class, userAccountId));
    }

    @Override
    public boolean existsByUserAndAcademy(final UUID userAccountId, final UUID academyId) {
        return memberJpaRepository.existsByUserAccountIdAndAcademyId(userAccountId, academyId);
    }

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll()
            .stream()
            .map(MemberJpaEntity::toAggregate)
            .toList();
    }
}
