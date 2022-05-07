package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.*;

public interface ISubscriptionService {
	
	List<Subscription> retrieveAllSubscriptions();

	Subscription addSubscription(Subscription s);

	void deleteSubscription(long id);

	Subscription updateSubscription(Subscription s);

	Subscription retrieveSubscription(long id);

}
