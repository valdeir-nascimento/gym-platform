package io.github.gym.platform.api.domain.teacher;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.user.UserAccountID;
import java.util.List;
import java.util.Optional;

public interface TeacherGateway {

    Teacher save(Teacher teacher);

    Optional<Teacher> findById(TeacherID id);

    Optional<Teacher> findByUserAndAcademy(UserAccountID userAccountId, AcademyID academyId);

    boolean existsByUserAndAcademy(UserAccountID userAccountId, AcademyID academyId);

    List<Teacher> findAllByAcademy(AcademyID academyId);
}
