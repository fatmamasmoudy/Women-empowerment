package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.JobOffer;

@Repository
public interface JobOfferRepository extends CrudRepository<JobOffer, Integer> {
           
}
