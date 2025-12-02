package io.github.gym.platform.api.infrastructure.security;

import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenService {

    private final SecurityAuthProperties properties;
    private final SecretKey secretKey;

    public JwtTokenService(final SecurityAuthProperties properties) {
        this.properties = properties;
        this.secretKey = Keys.hmacShaKeyFor(properties.jwt().secret().getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken generateToken(final UserAccount account) {
        final var now = Instant.now();
        final var expiresAt = now.plus(properties.jwt().expiration());

        final var token = Jwts.builder()
            .subject(account.getId().getValue().toString())
            .issuer(properties.jwt().issuer())
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiresAt))
            .claim("email", account.getEmail())
            .claim("name", account.getFullName())
            .claim("roles", account.getRoles().stream().map(UserRole::name).toList())
            .signWith(secretKey)
            .compact();

        return new JwtToken(token, expiresAt);
    }

    public Optional<JwtAuthenticatedUser> parse(final String token) {
        try {
            final Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

            final Claims body = claims.getPayload();
            final var id = UUID.fromString(body.getSubject());
            final var email = body.get("email", String.class);
            final var name = body.get("name", String.class);
            final var roles = body.get("roles", java.util.List.class);

            final Set<UserRole> roleSet = roles == null
                ? Set.of()
                : ((List<?>) roles).stream()
                    .map(Object::toString)
                    .map(UserRole::from)
                    .collect(Collectors.toUnmodifiableSet());

            return Optional.of(new JwtAuthenticatedUser(id, name, email, roleSet));
        } catch (final Exception ex) {
            return Optional.empty();
        }
    }

    public record JwtToken(String value, Instant expiresAt) {
    }
}

