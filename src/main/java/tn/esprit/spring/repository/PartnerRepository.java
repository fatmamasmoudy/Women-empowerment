package tn.esprit.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.*;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
	public Optional<Partner> findByEmail(String email);
	Boolean existsByEmail(String email);
	Partner findById(long id);
	

	
	@Query("select p from Partner p where p.isDisponible = true and p.address=:address")
	 List<Partner> FilterPartner(@Param("address")String address);
	
	@Query("select p from Partner p where CONCAT(p.name,p.address) LIKE %?1% and p.isDisponible=true")
	 List<Partner> SearchPartner(String keyword);
	
	@Query("select p.modules from Partner p where p.id=:id")
	  List<Module> GetModule(@Param("id") long id);
}
