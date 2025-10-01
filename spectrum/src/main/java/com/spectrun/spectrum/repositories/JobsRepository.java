package com.spectrun.spectrum.repositories;

import com.spectrun.spectrum.models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobsRepository  extends JpaRepository<Jobs,Long> {
    Optional<Jobs> findByIdempotencyKey(String idempotencyKey);
}
