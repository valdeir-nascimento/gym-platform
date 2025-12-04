package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.exception.NotFoundException;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.infrastructure.persistence.entity.UserAccountEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.UserAccountJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserAccountGatewayImpl implements UserAccountGateway {

    private final UserAccountJpaRepository userAccountJpaRepository;

    public UserAccountGatewayImpl(final UserAccountJpaRepository userAccountJpaRepository) {
        this.userAccountJpaRepository = Objects.requireNonNull(userAccountJpaRepository);
    }

    @Override
    public UserAccount save(final UserAccount account) {
        final var entity = userAccountJpaRepository
            .findById(account.getId().getValue())
            .map(user -> user.updateFrom(account))
            .orElseGet(() -> UserAccountEntity.from(account));  // cria a entidade e deixa o Hibernate persistir
        return userAccountJpaRepository.save(entity).toAggregate();
    }

    @Override
    public UserAccount findByEmail(final String email) {
        return userAccountJpaRepository.findByEmailIgnoreCase(email)
            .map(UserAccountEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(UserAccount.class, email));
    }

    @Override
    public UserAccount findByCpf(final String cpf) {
        return userAccountJpaRepository.findByCpf(cpf)
            .map(UserAccountEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(UserAccount.class, cpf));
    }

    @Override
    public boolean existsByEmail(final String email) {
        return userAccountJpaRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByCpf(final String cpf) {
        return userAccountJpaRepository.existsByCpf(cpf);
    }

    @Override
    public UserAccount findById(final UserAccountID id) {
        return userAccountJpaRepository.findById(id.getValue())
            .map(UserAccountEntity::toAggregate)
            .orElseThrow(() -> NotFoundException.with(UserAccount.class, id.getValue()));
    }
}

