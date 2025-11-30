package io.github.gym.platform.api.domain.academy;

import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

import java.time.Instant;

public class Academy extends AggregateRoot<AcademyID> {

    private final String name;
    private final String cnpj;
    private final String phone;
    private final String email;
    private final String address;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Academy(
            final AcademyID id,
            final String name,
            final String cnpj,
            final String phone,
            final String email,
            final String address,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id); // nenhuma validação aqui, tudo vai para o Validator
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create a brand new Academy.
     */
    public static Academy newAcademy(
            final String name,
            final String cnpj,
            final String phone,
            final String email,
            final String address
    ) {
        final var now = Instant.now();
        final var id = AcademyID.unique();

        return new Academy(
                id,
                name,
                cnpj,
                phone,
                email,
                address,
                true,   // new academies start as active by default
                now,
                now
        );
    }

    /**
     * Factory method to rehydrate an existing Academy from persistence.
     */
    public static Academy with(
            final AcademyID id,
            final String name,
            final String cnpj,
            final String phone,
            final String email,
            final String address,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Academy(
                id,
                name,
                cnpj,
                phone,
                email,
                address,
                active,
                createdAt,
                updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new AcademyValidator(this, handler).validate();
    }

    // Getters (somente leitura, mantendo o agregado imutável)

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
