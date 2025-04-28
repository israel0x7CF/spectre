package com.spectrun.spectrum.services.Implementations;

import com.spectrun.spectrum.DTO.SubscriptionDTO;
import com.spectrun.spectrum.models.Plan;
import com.spectrun.spectrum.models.Subscriptions;
import com.spectrun.spectrum.repositories.PlanRepository;
import com.spectrun.spectrum.repositories.SubscriptionRepository;
import com.spectrun.spectrum.services.Isubscription;
import com.spectrun.spectrum.utils.exceptions.PlanNotFoundException;
import org.springframework.stereotype.Service;
import com.spectrun.spectrum.utils.mappers.SubscriptionMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService implements Isubscription {
    private SubscriptionRepository subscriptionRepository;
    private PlanRepository planRepository;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JWTService.class.getName());

    public SubscriptionService(SubscriptionRepository subscriptionRepository,PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    public Subscriptions subscriptionsMapper(SubscriptionDTO subscriptionDTO){
        return  SubscriptionMapper.INSTANCE.toEntity(subscriptionDTO);
    }
   public  SubscriptionDTO subscriptionDTOMapper(Subscriptions subscriptions){
        return  SubscriptionMapper.INSTANCE.toDto(subscriptions);
   }
    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        long planId = subscriptionDTO.getPlanId();
        Plan plan = this.planRepository.findById(planId).orElseThrow(()-> new PlanNotFoundException("Subscription Plan Not Found"));
        Subscriptions newSubscription = subscriptionsMapper(subscriptionDTO);
        newSubscription.setPlan(plan);
        this.subscriptionRepository.save(newSubscription);
        return subscriptionDTOMapper(newSubscription);
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptions() {
        List<Subscriptions> subscriptionList = this.subscriptionRepository.findAll();
        return subscriptionList.stream().map(subscriptions -> subscriptionDTOMapper(subscriptions)).collect(Collectors.toList());
    }

    @Override
    public Subscriptions getSubscriptionById(long id) {
        Subscriptions subscriptions = this.subscriptionRepository.findById(id).orElse(null);
        if(subscriptions != null){
            logger.info(subscriptions.getStatus());
            return  subscriptions;
        }
        return  null;

    }
}
