package io.github.gym.platform.api.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "security.auth")
public class SecurityAuthProperties {

    private final Jwt jwt = new Jwt();
    private final Bootstrap bootstrap = new Bootstrap();

    public Jwt jwt() {
        return jwt;
    }

    public Bootstrap bootstrap() {
        return bootstrap;
    }

    public static class Jwt {
        private String issuer = "gym-platform";
        private String secret = "change-me-change-me-change-me-change-me-change-me";
        private long expirationMinutes = 60;

        public String issuer() {
            return issuer;
        }

        public void setIssuer(final String issuer) {
            this.issuer = issuer;
        }

        public String secret() {
            return secret;
        }

        public void setSecret(final String secret) {
            this.secret = secret;
        }

        public long expirationMinutes() {
            return expirationMinutes;
        }

        public void setExpirationMinutes(final long expirationMinutes) {
            this.expirationMinutes = expirationMinutes;
        }

        public Duration expiration() {
            return Duration.ofMinutes(expirationMinutes);
        }
    }

    public static class Bootstrap {
        private boolean enabled = false;
        private String fullName = "Admin";
        private String email = "admin@gym.io";
        private String phone;
        private String cpf = "00000000000";
        private LocalDate birthDate = LocalDate.of(1990, 1, 1);
        private String password = "changeit";
        private List<String> roles = new ArrayList<>(List.of("ADMIN"));

        public boolean enabled() {
            return enabled;
        }

        public void setEnabled(final boolean enabled) {
            this.enabled = enabled;
        }

        public String fullName() {
            return fullName;
        }

        public void setFullName(final String fullName) {
            this.fullName = fullName;
        }

        public String email() {
            return email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }

        public String phone() {
            return phone;
        }

        public void setPhone(final String phone) {
            this.phone = phone;
        }

        public String cpf() {
            return cpf;
        }

        public void setCpf(final String cpf) {
            this.cpf = cpf;
        }

        public LocalDate birthDate() {
            return birthDate;
        }

        public void setBirthDate(final LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public String password() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }

        public List<String> roles() {
            return roles;
        }

        public void setRoles(final List<String> roles) {
            this.roles = roles == null ? List.of() : roles;
        }
    }
}

