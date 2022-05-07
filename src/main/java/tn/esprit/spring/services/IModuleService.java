package tn.esprit.spring.services;



import java.util.List;

import tn.esprit.spring.entities.Module;


public interface IModuleService {
	 void ajouterModule(Module module,long idPartner);
	 List<Module> listerModules();
	 void SupprimerModule(int id);
	 Module UpdateModule(Module m);
	//List<Module> getTraitementModule();
	 
	     


}
