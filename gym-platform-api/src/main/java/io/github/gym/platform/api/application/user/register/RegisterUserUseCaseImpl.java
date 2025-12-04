package io.github.gym.platform.api.application.user.register;

import io.github.gym.platform.api.application.user.UserAccountOutput;
import io.github.gym.platform.api.application.user.register.command.RegisterUserCommand;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.user.UserAccount;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.domain.user.UserRole;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserAccountGateway userAccountGateway;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCaseImpl(final UserAccountGateway userAccountGateway, final PasswordEncoder passwordEncoder) {
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Override
    public UserAccountOutput execute(final RegisterUserCommand command) {
        final var notification = Notification.create();

        final var roles = resolveRoles(command.roles(), notification);

        final var encodedPassword = command.password() == null ? null : passwordEncoder.encode(command.password());

        final var account = UserAccount.newAccount(
            command.fullName(),
            command.email(),
            command.phone(),
            command.cpf(),
            command.birthDate(),
            encodedPassword,
            roles
        );

        account.validate(notification);

        if (command.email() != null && userAccountGateway.existsByEmail(command.email())) {
            notification.append(Error.of("'email' is already in use"));
        }

        if (command.cpf() != null && userAccountGateway.existsByCpf(command.cpf())) {
            notification.append(Error.of("'cpf' is already in use"));
        }

        if (notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        final var saved = userAccountGateway.save(account);

        return UserAccountOutput.from(saved);
    }

    private Set<UserRole> resolveRoles(final Set<String> roles, final Notification notification) {
        if (CollectionUtils.isEmpty(roles)) {
            notification.append(Error.of("'roles' must contain at least one role"));
            return Set.of();
        }

        return roles.stream()
            .filter(Objects::nonNull)
            .map(String::trim)
            .filter(role -> !role.isBlank())
            .map(role -> this.toRole(role, notification))
            .filter(Objects::nonNull)
            .collect(Collectors.toUnmodifiableSet());
    }

    private UserRole toRole(final String role, final Notification notification) {
        try {
            return UserRole.from(role);
        } catch (final IllegalArgumentException ex) {
            notification.append(Error.of("Role '%s' is invalid".formatted(role)));
            return null;
        }
    }
}

