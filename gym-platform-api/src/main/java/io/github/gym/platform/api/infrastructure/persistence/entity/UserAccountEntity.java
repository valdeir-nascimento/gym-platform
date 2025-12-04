package io.github.gym.platform.api.infrastructure.persistence.entity;

import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountID;
import io.github.gym.platform.api.domain.user.UserRole;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<TeacherJpaEntity> teachers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<MemberJpaEntity> members;

    public UserAccountEntity() {
    }

    private UserAccountEntity(
        final UUID id,
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
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TeacherJpaEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherJpaEntity> teachers) {
        this.teachers = teachers;
    }

    public List<MemberJpaEntity> getMembers() {
        return members;
    }

    public void setMembers(List<MemberJpaEntity> members) {
        this.members = members;
    }

    public static UserAccountEntity from(final UserAccount account) {
        return new UserAccountEntity(
            account.getId().getValue(),
            account.getFullName(),
            account.getEmail(),
            account.getPhone(),
            account.getCpf(),
            account.getBirthDate(),
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
            cpf,
            birthDate,
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

    public UserAccountEntity updateFrom(final UserAccount account) {
        this.fullName = account.getFullName();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.cpf = account.getCpf();
        this.birthDate = account.getBirthDate();
        this.password = account.getPassword();
        this.active = account.isActive();
        this.roles = new HashSet<>(account.getRoles());
        return this;
    }
}

