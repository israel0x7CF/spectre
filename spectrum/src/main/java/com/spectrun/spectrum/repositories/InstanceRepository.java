package com.spectrun.spectrum.repositories;

import com.spectrun.spectrum.Enums.Status;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Users;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstanceRepository extends JpaRepository<Instances,Long> {
List<Instances> findByUserId(Users user);
Instances findByinstanceName(String instanceName);
List<Instances> findByStatus(Status status);
}
