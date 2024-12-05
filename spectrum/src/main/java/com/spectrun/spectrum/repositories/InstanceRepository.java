package com.spectrun.spectrum.repositories;

import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstanceRepository extends JpaRepository<Instances,Long> {
List<Instances> findByUserId(Users user);
}
