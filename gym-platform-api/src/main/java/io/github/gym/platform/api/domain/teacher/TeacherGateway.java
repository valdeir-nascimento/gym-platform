package io.github.gym.platform.api.domain.teacher;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.user.UserAccountID;

import java.util.List;

public interface TeacherGateway {

    Teacher save(Teacher teacher);

    Teacher findById(TeacherID id);

    Teacher findByUserAndAcademy(UserAccountID userAccountId, AcademyID academyId);

    boolean existsByUserAndAcademy(UserAccountID userAccountId, AcademyID academyId);

    List<Teacher> findAllByAcademy(AcademyID academyId);
}
