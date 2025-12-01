package io.github.gym.platform.api.domain.user;

import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserAccount extends AggregateRoot<UserAccountID> {

    private final String fullName;
    private final String email;
    private final String phone;
    private final String password;
    private final boolean active;
    private final Set<UserRole> roles;
    private final Instant createdAt;
    private final Instant updatedAt;

    private UserAccount(
        final UserAccountID id,
        final String fullName,
        final String email,
        final String phone,
        final String password,
        final boolean active,
        final Set<UserRole> roles,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        super(Objects.requireNonNull(id, "'id' must not be null"));
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.active = active;
        this.roles = Collections.unmodifiableSet(new HashSet<>(roles != null ? roles : Set.of()));
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserAccount newAccount(
        final String fullName,
        final String email,
        final String phone,
        final String password,
        final Set<UserRole> roles
    ) {
        final var now = Instant.now();
        final var id = UserAccountID.unique();

        final var effectiveRoles = roles != null ? Set.copyOf(roles) : Set.<UserRole>of();

        return new UserAccount(
            id,
            fullName,
            email,
            phone,
            password,
            true,
            effectiveRoles,
            now,
            now
        );
    }

    public static UserAccount with(
        final UserAccountID id,
        final String fullName,
        final String email,
        final String phone,
        final String password,
        final boolean active,
        final Set<UserRole> roles,
        final Instant createdAt,
        final Instant updatedAt
    ) {
        return new UserAccount(
            id,
            fullName,
            email,
            phone,
            password,
            active,
            roles != null ? roles : Set.of(),
            createdAt,
            updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserAccountValidator(this, handler).validate();
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
