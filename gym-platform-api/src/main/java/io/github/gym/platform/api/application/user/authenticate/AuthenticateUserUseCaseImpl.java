package io.github.gym.platform.api.application.user.authenticate;

import io.github.gym.platform.api.application.user.authenticate.command.AuthenticateUserCommand;
import io.github.gym.platform.api.domain.exception.AuthenticationException;
import io.github.gym.platform.api.domain.user.UserAccountGateway;
import io.github.gym.platform.api.infrastructure.security.JwtTokenService;
import io.github.gym.platform.api.infrastructure.security.JwtTokenService.JwtToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final UserAccountGateway userAccountGateway;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthenticateUserUseCaseImpl(
        final UserAccountGateway userAccountGateway,
        final PasswordEncoder passwordEncoder,
        final JwtTokenService jwtTokenService
    ) {
        this.userAccountGateway = Objects.requireNonNull(userAccountGateway);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.jwtTokenService = Objects.requireNonNull(jwtTokenService);
    }

    @Override
    public AuthenticationTokenOutput execute(final AuthenticateUserCommand command) {
        final var account = userAccountGateway.findByEmail(command.email())
            .orElseThrow(AuthenticationException::invalidCredentials);

        if (!account.isActive()) {
            throw AuthenticationException.invalidCredentials();
        }

        if (command.password() == null || !passwordEncoder.matches(command.password(), account.getPassword())) {
            throw AuthenticationException.invalidCredentials();
        }

        final JwtToken token = jwtTokenService.generateToken(account);
        return new AuthenticationTokenOutput(token.value(), token.expiresAt());
    }
}

