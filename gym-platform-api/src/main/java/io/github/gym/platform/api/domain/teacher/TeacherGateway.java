package io.github.gym.platform.api.domain.teacher;

import io.github.gym.platform.api.domain.academy.AcademyID;

import java.util.List;

public interface TeacherGateway {

    Teacher save(Teacher teacher);

    Teacher findById(TeacherID id);

    List<Teacher> findAllByAcademy(AcademyID academyId);
}
