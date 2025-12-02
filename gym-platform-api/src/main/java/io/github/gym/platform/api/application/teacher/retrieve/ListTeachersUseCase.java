package io.github.gym.platform.api.application.teacher.retrieve;

import io.github.gym.platform.api.application.QueryUseCase;
import io.github.gym.platform.api.application.teacher.TeacherOutput;
import io.github.gym.platform.api.application.teacher.retrieve.query.ListTeachersQuery;

import java.util.List;

public interface ListTeachersUseCase extends QueryUseCase<ListTeachersQuery, List<TeacherOutput>> {
}

