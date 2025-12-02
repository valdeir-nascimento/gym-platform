package io.github.gym.platform.api.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.presentation.rest.error.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final var apiError = new ApiError(
            "Unauthorized",
            List.of(Error.of(authException.getMessage() == null ? "Unauthorized" : authException.getMessage()))
        );

        objectMapper.writeValue(response.getOutputStream(), apiError);
    }
}

