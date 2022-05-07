package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Activity;
import tn.esprit.spring.repository.ActivityRepository;

@Service
public class IActivityServiceImpl implements IactivityService{

	@Autowired
	ActivityRepository ar;
	@Override
	public void addActivity(Activity act) {
		ar.save(act);
		
	}

}
