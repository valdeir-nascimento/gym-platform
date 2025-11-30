package io.github.gym.platform.api.domain.academy;

import java.util.List;
import java.util.Optional;

public interface AcademyGateway {

    Academy save(Academy academy);

    Optional<Academy> findById(AcademyID id);

    Optional<Academy> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    List<Academy> findAll();

    List<Academy> findByName(String name);
}
