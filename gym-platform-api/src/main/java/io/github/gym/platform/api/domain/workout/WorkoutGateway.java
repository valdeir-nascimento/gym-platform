package io.github.gym.platform.api.domain.workout;

import io.github.gym.platform.api.domain.academy.AcademyID;
import io.github.gym.platform.api.domain.member.MemberID;
import io.github.gym.platform.api.domain.teacher.TeacherID;

import java.util.List;

public interface WorkoutGateway {
    Workout save(Workout workout);

    Workout findById(WorkoutID id);

    List<Workout> findAllByMember(MemberID memberId);

    List<Workout> findAllByAcademy(AcademyID academyId);

    List<Workout> findAllByTeacher(TeacherID teacherId);
}
