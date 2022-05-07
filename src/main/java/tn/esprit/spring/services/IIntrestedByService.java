package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.*;

public interface IIntrestedByService {
	
	List<IntrestedBy> retrieveAllIntrestedBy();

	IntrestedBy addIntrestedBy(IntrestedBy i);

	void deleteIntrestedBy(long id);

	IntrestedBy updateIntrestedBy(IntrestedBy i);

	IntrestedBy retrieveIntrestedBy(long id);


}
