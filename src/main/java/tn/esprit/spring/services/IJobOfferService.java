package tn.esprit.spring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.JobOffer;

@Service
public interface IJobOfferService {

	void AddJobOffer (JobOffer jobOffer) ;
	 void deleteJobOffer(Integer id);
	 JobOffer updateJobOffer(JobOffer jobOffer);
	 
	 List<JobOffer> getAllJobs();
	
	 
	 
	 
		//List<JobOffer> findById(Integer idJOffer);
	//Iterable<JobOffer> listAllJobOffers();
	//JobOffer getJobOfferById(Integer id);
	//	List<JobOffer> findById(Integer idJOffer);

	
	
  //  void UpdateJobOffer(Integer id, JobOffer jobOffer);

    
    
	
}
