package io.github.gym.platform.api.infrastructure.security;

import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserRole;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SecurityBootstrap implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityBootstrap.class);

    private final SecurityAuthProperties properties;
    private final UserAccountGateway userAccountGateway;
    private final PasswordEncoder passwordEncoder;

    public SecurityBootstrap(
        final SecurityAuthProperties properties,
        final UserAccountGateway userAccountGateway,
        final PasswordEncoder passwordEncoder
    ) {
        this.properties = Objects.requireNonNull(properties);
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Override
    public void run(final ApplicationArguments args) {
        final var bootstrap = properties.bootstrap();

        if (!bootstrap.enabled()) {
            return;
        }

        if (bootstrap.email() == null || bootstrap.email().isBlank()) {
            LOGGER.warn("Bootstrap user email is not configured, skipping creation");
            return;
        }

        if (userAccountGateway.existsByEmail(bootstrap.email())) {
            return;
        }

        final Set<UserRole> roles = bootstrap.roles().stream()
            .map(role -> {
                try {
                    return UserRole.from(role);
                } catch (final IllegalArgumentException ex) {
                    LOGGER.warn("Ignoring invalid bootstrap role '{}'", role);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toUnmodifiableSet());

        final var account = UserAccount.newAccount(
            bootstrap.fullName(),
            bootstrap.email(),
            bootstrap.phone(),
            bootstrap.cpf(),
            bootstrap.birthDate(),
            passwordEncoder.encode(bootstrap.password()),
            roles
        );

        final var notification = Notification.create();
        account.validate(notification);

        if (notification.hasErrors()) {
            LOGGER.warn("Bootstrap user is invalid: {}", notification.getErrors());
            return;
        }

        userAccountGateway.save(account);
        LOGGER.info("Bootstrap user '{}' created", bootstrap.email());
    }
}

