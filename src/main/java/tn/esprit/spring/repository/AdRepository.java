package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Advertisement;
import tn.esprit.spring.entities.Domain;
import tn.esprit.spring.entities.Subject;
@Repository
public interface AdRepository extends CrudRepository<Advertisement, Integer> {
List <Advertisement> findByDomain( Domain domain);

}
