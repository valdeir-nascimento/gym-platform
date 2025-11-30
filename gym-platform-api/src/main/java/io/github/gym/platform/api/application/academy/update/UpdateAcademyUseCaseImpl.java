package io.github.gym.platform.api.application.academy.update;

import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.update.command.UpdateAcademyCommand;
import io.github.gym.platform.api.domain.academy.AcademyGateway;
import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.validation.handler.Notification;
import org.springframework.stereotype.Service;

@Service
public class UpdateAcademyUseCaseImpl implements UpdateAcademyUseCase {

    private final AcademyGateway academyGateway;

    public UpdateAcademyUseCaseImpl(final AcademyGateway academyGateway) {
        this.academyGateway = academyGateway;
    }

    @Override
    public AcademyOutput execute(final UpdateAcademyCommand command) {
        final var existing = academyGateway.findById(AcademyID.from(command.id()));

        existing.update(
            command.name(),
            command.cnpj(),
            command.phone(),
            command.email(),
            command.address(),
            command.active()
        );

        final var notification = Notification.create();

        existing.validate(notification);

        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.getErrors().toString());
        }

        final var saved = academyGateway.save(existing);

        return AcademyOutput.from(saved);
    }
}