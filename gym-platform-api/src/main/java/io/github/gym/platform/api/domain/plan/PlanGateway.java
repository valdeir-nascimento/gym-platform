package io.github.gym.platform.api.domain.plan;

import io.github.gym.platform.api.domain.academy.AcademyID;

import java.util.List;
import java.util.Optional;

public interface PlanGateway {

    Plan save(Plan plan);

    Optional<Plan> findById(PlanID id);

    List<Plan> findAllByAcademy(AcademyID academyId);

    boolean existsByNameAndAcademy(String name, AcademyID academyId);
}
