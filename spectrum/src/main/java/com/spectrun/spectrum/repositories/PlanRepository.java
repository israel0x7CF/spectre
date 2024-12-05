package com.spectrun.spectrum.repositories;

import com.spectrun.spectrum.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan,Long> {
}
