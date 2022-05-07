package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Condidacy;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.User;

@Repository
public interface CondidacyRepository extends CrudRepository<Condidacy, Integer> {
	
	@Query("select c from Condidacy c where c.state = 'In_Progress' And c.module.traitement ='Automatique'")
	List<Condidacy> getModuleAutomatique();
	
	@Query("select c from Condidacy c where c.state='Accepted' and c.module=:module")
	List<Condidacy> findByModule(@Param("module")Module module);
	
	
	@Query("select u.condidaciesC from User u where u.id=:id ")
	List<Condidacy> findCondidacyUser(@Param("id") long id);
	
	

}
