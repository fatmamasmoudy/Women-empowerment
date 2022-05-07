package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Certificate;

@Repository
public interface CertificateRepository extends CrudRepository<Certificate, Integer> {

	List<Certificate> findByTraining(int trainingId);
}
