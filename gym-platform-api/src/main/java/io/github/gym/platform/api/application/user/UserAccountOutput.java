package io.github.gym.platform.api.application.user;

import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserRole;

import java.time.Instant;
import java.util.Set;

public record UserAccountOutput(
    String id,
    String fullName,
    String email,
    String phone,
    boolean active,
    Set<UserRole> roles,
    Instant createdAt,
    Instant updatedAt
) {

    public static UserAccountOutput from(final UserAccount account) {
        return new UserAccountOutput(
            account.getId().getValue().toString(),
            account.getFullName(),
            account.getEmail(),
            account.getPhone(),
            account.isActive(),
            Set.copyOf(account.getRoles()),
            account.getCreatedAt(),
            account.getUpdatedAt()
        );
    }
}

