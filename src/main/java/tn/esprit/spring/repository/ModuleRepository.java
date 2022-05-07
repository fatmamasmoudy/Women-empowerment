package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Module;

@Repository
public interface ModuleRepository extends CrudRepository<Module, Integer> {

	@Query("select m from Module m where m.name= :interet")
	List<Module> findByNam(@Param("interet")String interet);
	
	
	
}
