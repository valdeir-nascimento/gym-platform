package io.github.gym.platform.api.domain.user;

import java.util.Optional;

public interface UserAccountGateway {

    UserAccount save(UserAccount account);

    Optional<UserAccount> findByEmail(String email);

    boolean existsByEmail(String email);
}

