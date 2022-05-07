package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Event;

@Repository
public interface EventRepository extends CrudRepository <Event , Integer>{

	
	@Query("SELECT e FROM Event e WHERE e.description LIKE %:description%")
	List<Event> retrieveEventsearch(@Param("description") String
	description);
}
