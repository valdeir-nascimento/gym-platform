package io.github.gym.platform.api.application.member.update;

import io.github.gym.platform.api.application.CommandUseCase;
import io.github.gym.platform.api.application.member.MemberOutput;
import io.github.gym.platform.api.application.member.update.command.UpdateMemberCommand;

public interface UpdateMemberUseCase extends CommandUseCase<UpdateMemberCommand, MemberOutput> {
}

