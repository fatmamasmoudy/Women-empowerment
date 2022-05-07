package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Subject;
 @Repository
public interface SubjectRepository extends CrudRepository<Subject, Integer> {

	
	@Query(value = "SELECT * FROM `subject` WHERE `created_at` < DATE_SUB(now(), INTERVAL 6 MONTH)" ,
			nativeQuery = true)
			List<Subject> retrieveClientsByDateSql();

	  @Query( "SELECT s FROM Subject s WHERE s.Description LIKE %:des% and s.Name LIKE %:nom%")
	  List<Subject> exists(@Param("des") String des, @Param("nom") String name);
			
	
	
}
