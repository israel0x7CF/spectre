package com.spectrun.spectrum.repositories;


import com.spectrun.spectrum.models.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Modules,Long> {
}
