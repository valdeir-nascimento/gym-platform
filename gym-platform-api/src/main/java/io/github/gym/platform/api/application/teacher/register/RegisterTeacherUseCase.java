package io.github.gym.platform.api.application.teacher.register;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.teacher.TeacherOutput;
import io.github.gym.platform.api.application.teacher.register.command.RegisterTeacherCommand;

public interface RegisterTeacherUseCase extends CommandUseCase<RegisterTeacherCommand, TeacherOutput> {
}

