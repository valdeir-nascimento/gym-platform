package io.github.gym.platform.api.infrastructure.persistence.gateway;

import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.infrastructure.persistence.entity.UserAccountEntity;
import io.github.gym.platform.api.infrastructure.persistence.repository.UserAccountJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserAccountGatewayImpl implements UserAccountGateway {

    private final UserAccountJpaRepository repository;

    public UserAccountGatewayImpl(final UserAccountJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public UserAccount save(final UserAccount account) {
        final var entity = UserAccountEntity.from(account);
        final var saved = repository.save(entity);
        return saved.toAggregate();
    }

    @Override
    public Optional<UserAccount> findByEmail(final String email) {
        return repository.findByEmailIgnoreCase(email).map(UserAccountEntity::toAggregate);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Optional<UserAccount> findById(final UserAccountID id) {
        return repository.findById(id.getValue())
            .map(UserAccountEntity::toAggregate);
    }
}

