package io.github.gym.platform.api.domain.user;

import java.util.Optional;

public interface UserAccountGateway {

    UserAccount save(UserAccount account);

    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}

