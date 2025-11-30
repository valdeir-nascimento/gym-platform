package io.github.gym.platform.api.application.academy;

import io.github.gym.platform.api.domain.academy.Academy;

import java.time.Instant;

public record AcademyOutput(
    String id,
    String name,
    String cnpj,
    String phone,
    String email,
    String address,
    boolean active,
    Instant createdAt,
    Instant updatedAt
) {

    public static AcademyOutput from(final Academy academy) {
        return new AcademyOutput(
            academy.getId().getValue().toString(),
            academy.getName(),
            academy.getCnpj(),
            academy.getPhone(),
            academy.getEmail(),
            academy.getAddress(),
            academy.isActive(),
            academy.getCreatedAt(),
            academy.getUpdatedAt()
        );
    }
}
