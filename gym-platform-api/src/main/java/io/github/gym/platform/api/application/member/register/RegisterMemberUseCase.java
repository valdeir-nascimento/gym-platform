package io.github.gym.platform.api.application.member.register;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.member.register.command.RegisterMemberCommand;

public interface RegisterMemberUseCase extends CommandUseCase<RegisterMemberCommand, MemberRegisterOutput> {
}

