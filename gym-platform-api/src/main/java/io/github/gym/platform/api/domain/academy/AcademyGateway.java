package io.github.gym.platform.api.domain.academy;

public interface AcademyGateway {

    Academy save(Academy academy);

    Academy findById(AcademyID id);

    Academy findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}
