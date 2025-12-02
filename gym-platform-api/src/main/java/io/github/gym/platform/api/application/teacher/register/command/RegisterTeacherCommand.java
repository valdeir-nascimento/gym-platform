package io.github.gym.platform.api.application.teacher.register.command;

public record RegisterTeacherCommand(
    String fullName,
    String email,
    String phone,
    String password,
    String academyId,
    String specialization
) {

    public static RegisterTeacherCommand with(
        final String fullName,
        final String email,
        final String phone,
        final String password,
        final String academyId,
        final String specialization
    ) {
        return new RegisterTeacherCommand(fullName, email, phone, password, academyId, specialization);
    }
}

