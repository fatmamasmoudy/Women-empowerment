package tn.esprit.spring.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Penality;
import tn.esprit.spring.entities.Training;
import tn.esprit.spring.entities.TypeAvertissement;
import tn.esprit.spring.entities.User;

public interface IPenalityService {
	
	Penality addPenalityToLearner(Penality penality, long idLearner, int idTraining);
	
	Penality updatePenality(Penality penality);
	
	void deletePenality(int idPenality);
	
	List<Penality> findAllPenalities();
	
	List<Penality> findByLearner(long idLearner);
	
	List<Penality> exclure(long learner,int training);

}
