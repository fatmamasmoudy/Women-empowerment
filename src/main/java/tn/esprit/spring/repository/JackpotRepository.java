package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Jackpot;

@Repository
public interface JackpotRepository extends CrudRepository <Jackpot , Integer> {
	@Query("SELECT j FROM Jackpot j WHERE j.id - :id  > 0 AND j.event = null")
	Jackpot retrivenextjackpot(@Param("id") int
	id);
	
}
