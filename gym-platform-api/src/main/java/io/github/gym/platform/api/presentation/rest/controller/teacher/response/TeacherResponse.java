package io.github.gym.platform.api.presentation.rest.controller.teacher.response;

import io.github.gym.platform.api.application.teacher.TeacherOutput;

public record TeacherResponse(
    String id,
    String fullName,
    String email,
    String phone,
    String specialization,
    String status,
    String hiredAt
) {

    public static TeacherResponse from(final TeacherOutput output) {
        return new TeacherResponse(
            output.id(),
            output.fullName(),
            output.email(),
            output.phone(),
            output.specialization(),
            output.status().name(),
            output.hiredAt() != null ? output.hiredAt().toString() : null
        );
    }
}

