package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.*;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Override
	public List<Subscription> retrieveAllSubscriptions() {
		return (List<Subscription>) subscriptionRepository.findAll();
	}
	
	@Override
	public Subscription addSubscription(Subscription u) {
		subscriptionRepository.save(u);
		return u;
	}

	@Override
	public void deleteSubscription (long id) {
		subscriptionRepository.deleteById( id);
	}

	@Override
	public Subscription updateSubscription(Subscription u) {
		subscriptionRepository.save(u);
		return u;
	}

	@Override
	public Subscription retrieveSubscription(long id) {
		Subscription u = subscriptionRepository.findById(id).get();
		return u;
	}

}
