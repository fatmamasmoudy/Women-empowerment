package tn.esprit.spring.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Training;
import tn.esprit.spring.entities.Domain;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Integer> {
	
	List<Training> findBySubjectContains(String subject);
	
	List<Training> findByTrainerName(String trainer);
	
	List<Training> findByEndDateIsBefore(Date d);
	
	List<Training> findByDomain(Domain domain);
	
	//List<Training> findByDomainAnd(Domain domain);
	
	@Query("SELECT count(t) FROM Training t WHERE t.endDate >  CURRENT_DATE AND t.trainerName=:trainer ")
	int nbreTrainingInProgressByTrainerName(@Param("trainer") String trainer);
	
	@Query("SELECT t FROM Training t WHERE t.trainerName like %:str% OR t.subject like %:str% OR t.domain like %:str%")
	List<Training> searchTraining(@Param("str") String str);
	
	@Query("SELECT count(t) FROM Training t WHERE t.endDate >  CURRENT_DATE AND t.trainerName=:trainer ")
	String GetDomain(@Param("trainer") String trainer);
	
	
	@Query("SELECT t FROM Training t WHERE t.endDate-t.startDate< :timing")
	List<Training> searchTrainingByTiming(@Param("timing") Double timing);
	
	
	@Query("SELECT t.endDate-t.startDate FROM Training t WHERE t.id='1' ")
	int duree();
	
	

	@Query("SELECT t FROM Training t WHERE t.endDate =  CURRENT_DATE And t.isCertified = true")
	List<Training> EndTraining();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Query("SELECT domain, COUNT(*) as c from training t where t.trainerName like %:str% OR t.subject like %:str% OR t.domain like %:str% GROUP BY domain ORDER by c DESC LIMIT '1'")
//	String  domain(@Param("str") String str);
	
	@Query(value = "SELECT domain from training t where t.trainer_Name like %:str% OR t.subject like %:str% OR t.domain like %:str% GROUP BY domain ORDER by  COUNT(*) DESC LIMIT 1", nativeQuery = true )
	String  domain(@Param("str") String str);
	
	

}
