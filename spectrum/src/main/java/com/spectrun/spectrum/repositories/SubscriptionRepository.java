package com.spectrun.spectrum.repositories;

import com.spectrun.spectrum.models.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscriptions,Long> {
}
