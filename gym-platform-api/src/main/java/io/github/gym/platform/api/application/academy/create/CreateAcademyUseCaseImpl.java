package io.github.gym.platform.api.application.academy.create;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.create.command.CreateAcademyCommand;
import io.github.gym.platform.api.domain.academy.Academy;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.exception.DomainException;
import io.github.gym.platform.api.domain.validation.Error;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateAcademyUseCaseImpl implements CreateAcademyUseCase {

    private final AcademyGateway academyGateway;

    public CreateAcademyUseCaseImpl(final AcademyGateway academyGateway) {
        this.academyGateway = Objects.requireNonNull(academyGateway);
    }

    @Override
    public AcademyOutput execute(final CreateAcademyCommand command) {
        final var academy = Academy.newAcademy(
            command.name(),
            command.cnpj(),
            command.phone(),
            command.email(),
            command.address()
        );

        final var notification = Notification.create();

        academy.validate(notification);

        if (academyGateway.existsByCnpj(academy.getCnpj())) {
            notification.append(Error.of("'cnpj' is already in use"));
        }

        if (notification.hasErrors()) {
            throw DomainException.with(notification.getErrors());
        }

        final var saved = academyGateway.save(academy);

        return AcademyOutput.from(saved);
    }
}