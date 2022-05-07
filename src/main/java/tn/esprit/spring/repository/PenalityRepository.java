package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Penality;
import tn.esprit.spring.entities.Training;
import tn.esprit.spring.entities.TypeAvertissement;
import tn.esprit.spring.entities.User;

@Repository
public interface PenalityRepository extends CrudRepository<Penality, Integer> {
	List<Penality> findByLearner(long idLearner);
	
	@Query("SELECT p FROM Penality p WHERE p.learner = :learner and p.training = :training and p.type = :type")
	List<Penality> exclure(@Param("learner") User learner,@Param("training") Training training,@Param("type") TypeAvertissement type);

}
