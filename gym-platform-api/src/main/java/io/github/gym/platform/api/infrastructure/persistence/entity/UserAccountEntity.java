package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.user.UserRole;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccountEntity {

    @Id
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_account_roles", joinColumns = @JoinColumn(name = "user_account_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public UserAccountEntity() {
    }

    private UserAccountEntity(
        final UUID id,
        final String fullName,
        final String email,
        final String phone,
        final String password,
        final boolean active,
        final Set<UserRole> roles,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserAccountEntity from(final UserAccount account) {
        return new UserAccountEntity(
            account.getId().getValue(),
            account.getFullName(),
            account.getEmail(),
            account.getPhone(),
            account.getPassword(),
            account.isActive(),
            new HashSet<>(account.getRoles()),
            account.getCreatedAt(),
            account.getUpdatedAt()
        );
    }

    public UserAccount toAggregate() {
        final var persistedRoles = roles == null ? Set.<UserRole>of() : Set.copyOf(roles);

        return UserAccount.with(
            UserAccountID.from(id),
            fullName,
            email,
            phone,
            password,
            active,
            persistedRoles,
            createdAt,
            updatedAt
        );
    }

    @PrePersist
    public void prePersist() {
        final var now = Instant.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}

