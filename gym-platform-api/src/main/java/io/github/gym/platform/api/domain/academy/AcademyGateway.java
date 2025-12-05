package io.github.gym.platform.api.domain.academy;

import java.util.List;

public interface AcademyGateway {

    Academy save(Academy academy);

    Academy findById(AcademyID id);

    Academy findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    List<Academy> findAll();

    void existById(AcademyID id);
}
