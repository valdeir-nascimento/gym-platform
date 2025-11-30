package io.github.gym.platform.api.application.academy.retrieve.query;

public record GetAcademyByCnpjQuery(String cnpj) {
    public static GetAcademyByCnpjQuery with(final String cnpj) {
        return new GetAcademyByCnpjQuery(cnpj);
    }
}
