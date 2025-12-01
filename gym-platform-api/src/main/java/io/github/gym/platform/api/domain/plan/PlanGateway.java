package io.github.gym.platform.api.domain.plan;

import io.github.gym.platform.api.domain.academy.AcademyID;

import java.util.List;

public interface PlanGateway {

    Plan save(Plan plan);

    Plan findById(PlanID id);

    List<Plan> findAllByAcademy(AcademyID academyId);
}
