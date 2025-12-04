package io.github.gym.platform.api.domain.user;

public interface UserAccountGateway {

    UserAccount save(UserAccount account);

    UserAccount findByEmail(String email);

    UserAccount findByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    UserAccount findById(UserAccountID id);
}

