package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.academy.AcademyID;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "academy")
public class AcademyEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "cnpj", length = 18, unique = true)
    private String cnpj;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected AcademyEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AcademyEntity that = (AcademyEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    public void onPrePersist() {
        final var now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.version == null) {
            this.version = 0L;
        }

        if (!this.active) {
            this.active = true;
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = Instant.now();
    }

    public static AcademyEntity from(final Academy academy) {
        final var entity = new AcademyEntity();

        if (academy.getId() != null) {
            entity.id = academy.getId().getValue();
        }

        entity.name = academy.getName();
        entity.cnpj = academy.getCnpj();
        entity.phone = academy.getPhone();
        entity.email = academy.getEmail();
        entity.address = academy.getAddress();
        entity.active = academy.isActive();

        return entity;
    }

    public Academy toAggregate() {
        return Academy.with(
            AcademyID.from(this.id),
            this.name,
            this.cnpj,
            this.phone,
            this.email,
            this.address,
            this.active,
            this.createdAt,
            this.updatedAt
        );
    }

    public AcademyEntity updateFrom(final Academy academy) {
        this.name = academy.getName();
        this.cnpj = academy.getCnpj();
        this.phone = academy.getPhone();
        this.email = academy.getEmail();
        this.address = academy.getAddress();
        this.active = academy.isActive();
        return this;
    }
}
