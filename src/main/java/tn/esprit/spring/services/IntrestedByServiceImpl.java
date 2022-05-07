package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.*;

@Service
public class IntrestedByServiceImpl implements IIntrestedByService{
	
	@Autowired
	IntrestedByRepository intrestedByRepository;
	@Override
	public List<IntrestedBy> retrieveAllIntrestedBy() {
		return (List<IntrestedBy>) intrestedByRepository.findAll();
	}
	
	@Override
	public IntrestedBy addIntrestedBy(IntrestedBy u) {
		intrestedByRepository.save(u);
		return u;
	}

	@Override
	public void deleteIntrestedBy (long id) {
		intrestedByRepository.deleteById( id);
	}

	@Override
	public IntrestedBy updateIntrestedBy(IntrestedBy u) {
		intrestedByRepository.save(u);
		return u;
	}

	@Override
	public IntrestedBy retrieveIntrestedBy(long id) {
		IntrestedBy u = intrestedByRepository.findById(id).get();
		return u;
	}

}
