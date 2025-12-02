package io.github.gym.platform.api.presentation.rest.controller.auth;

import io.github.gym.platform.api.application.user.UserAccountOutput;
import io.github.gym.platform.api.application.user.authenticate.AuthenticateUserUseCase;
import io.github.gym.platform.api.application.user.authenticate.command.AuthenticateUserCommand;
import io.github.gym.platform.api.application.user.register.RegisterUserUseCase;
import io.github.gym.platform.api.application.user.register.command.RegisterUserCommand;
import io.github.gym.platform.api.infrastructure.security.JwtAuthenticatedUser;
import io.github.gym.platform.api.presentation.rest.controller.auth.request.LoginRequest;
import io.github.gym.platform.api.presentation.rest.controller.auth.request.RegisterUserRequest;
import io.github.gym.platform.api.presentation.rest.controller.auth.response.AuthenticationResponse;
import io.github.gym.platform.api.presentation.rest.controller.auth.response.UserProfileResponse;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(
        final RegisterUserUseCase registerUserUseCase,
        final AuthenticateUserUseCase authenticateUserUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserAccountOutput> register(@RequestBody @Valid final RegisterUserRequest request) {
        final var command = RegisterUserCommand.with(
            request.fullName(),
            request.email(),
            request.phone(),
            request.password(),
            request.roles()
        );

        final var output = registerUserUseCase.execute(command);
        final var location = ApiUriFactory.createdLocation("/users/{userId}", output.id());
        return ResponseEntity.created(location).body(output);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid final LoginRequest request) {
        LOGGER.info("Login attempt for {}", request.email());
        final var command = AuthenticateUserCommand.with(request.email(), request.password());
        final var output = authenticateUserUseCase.execute(command);
        LOGGER.info("Login successful for {}", request.email());
        return ResponseEntity.ok(AuthenticationResponse.bearer(output.accessToken(), output.expiresAt()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me(@AuthenticationPrincipal final JwtAuthenticatedUser user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(UserProfileResponse.from(user));
    }
}

