package io.github.gym.platform.api.infrastructure.security;

import io.github.gym.platform.api.domain.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public record JwtAuthenticatedUser(
    UUID id,
    String fullName,
    String email,
    Set<UserRole> roles
) {

    public Collection<? extends GrantedAuthority> authorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .toList();
    }
}

