package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.CV;
@Repository
public interface CvsRepository extends CrudRepository<CV, Integer> {

}
