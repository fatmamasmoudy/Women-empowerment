package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.*;

public interface IReclamationService {
	
	List<Reclamation> retrieveAllReclamations();

	Reclamation addReclamation(Reclamation r);

	void deleteReclamation(long id);

	Reclamation updateReclamation(Reclamation r);

	Reclamation retrieveReclamation(long id);


}
