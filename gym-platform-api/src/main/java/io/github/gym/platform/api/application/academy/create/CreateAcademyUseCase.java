package io.github.gym.platform.api.application.academy.create;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.academy.AcademyOutput;
import io.github.gym.platform.api.application.academy.create.command.CreateAcademyCommand;

public interface CreateAcademyUseCase extends CommandUseCase<CreateAcademyCommand, AcademyOutput> {
}
