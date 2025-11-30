package io.github.gym.platform.api.domain.academy;

import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

import java.time.Instant;

public class Academy extends AggregateRoot<AcademyID> {

    private String name;
    private String cnpj;
    private String phone;
    private String email;
    private String address;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

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
        super(id);
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
            true,
            now,
            now
        );
    }

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

    public Academy update(
        final String name,
        final String cnpj,
        final String phone,
        final String email,
        final String address,
        final boolean active
    ) {
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.active = active;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new AcademyValidator(this, handler).validate();
    }

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
