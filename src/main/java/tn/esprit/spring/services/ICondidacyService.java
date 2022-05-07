package tn.esprit.spring.services;

import java.util.List;

import javax.mail.MessagingException;

import tn.esprit.spring.entities.Condidacy;



public interface ICondidacyService {

	 Condidacy  addCandidacy(Condidacy c,long idUser);
	 List<Condidacy> listCondidacy();
	 Condidacy UpdateCondidacy(Condidacy c);
	 void DeleteCondidacy(int id);
	 
	 void TraitementCondidacy();
	 void ModuleAutomatique()throws MessagingException;
}
