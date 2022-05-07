package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.*;

@Service
public class ReclamationServiceImpl implements IReclamationService {
	
	@Autowired
	ReclamationRepository reclamationRepository;
	@Autowired
	IUserService UserDAO;
	@Override
	public List<Reclamation> retrieveAllReclamations() {
		return (List<Reclamation>) reclamationRepository.findAll();
	}
	
	@Override
	public Reclamation addReclamation(Reclamation u) {
		reclamationRepository.save(u);
		return u;
	}

	@Override
	public void deleteReclamation (long id) {
		reclamationRepository.deleteById(id);
	}

	@Override
	public Reclamation updateReclamation(Reclamation u) {
		reclamationRepository.save(u);
		return u;
	}

	@Override
	public Reclamation retrieveReclamation(long id) {
		Reclamation u = reclamationRepository.findById(id).get();
		return u;
	}

}
