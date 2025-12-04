package io.github.gym.platform.api.domain.user;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import io.github.gym.platform.api.domain.core.AggregateRoot;
import io.github.gym.platform.api.domain.validation.ValidationHandler;

public class UserAccount extends AggregateRoot<UserAccountID> {

    private final String fullName;
    private final String email;
    private final String phone;
    private final String cpf;
    private final LocalDate birthDate;
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
        final String cpf,
        final LocalDate birthDate,
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
        this.cpf = cpf;
        this.birthDate = birthDate;
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
        final String cpf,
        final LocalDate birthDate,
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
            cpf,
            birthDate,
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
        final String cpf,
        final LocalDate birthDate,
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
            cpf,
            birthDate,
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

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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

    public UserAccount updateProfile(
        final String fullName,
        final String email,
        final String phone,
        final String cpf,
        final LocalDate birthDate
    ) {
        return new UserAccount(
            getId(),
            fullName,
            email,
            phone,
            cpf,
            birthDate,
            password,
            active,
            roles,
            createdAt,
            Instant.now()
        );
    }

}
