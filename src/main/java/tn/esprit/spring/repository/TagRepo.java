package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.IntrestedBy;
 @Repository
public interface TagRepo  extends CrudRepository<IntrestedBy, Integer> {	
	
 
 }
