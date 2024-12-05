package com.spectrun.spectrum.services;

import com.spectrun.spectrum.DTO.SubscriptionDTO;
import com.spectrun.spectrum.models.Subscriptions;

import java.util.List;

public interface Isubscription {
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);
    public List<SubscriptionDTO> getAllSubscriptions();
    public Subscriptions getSubscriptionById(long id);
}
