package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.repository.CondidacyRepository;
import tn.esprit.spring.repository.ModuleRepository;
import tn.esprit.spring.repository.PartnerRepository;

@Service
public class ModuleServiceImpl implements IModuleService {

	
	
	@Autowired
	CondidacyRepository condidacyRepo;
	@Autowired
	PartnerRepository partnerRepo;
	@Autowired
	ModuleRepository moduleRepo;
	
	@Override
	public void ajouterModule(Module module,long idPartner) {
		
		Partner p=partnerRepo.findById(idPartner);
		module.setPartner(p);
		
		moduleRepo.save(module);
	}


	@Override
	public List<Module> listerModules() {
			return (List<Module>) moduleRepo.findAll();
	}


	@Override
	public void SupprimerModule(int id) {
		Module m= moduleRepo.findById(id).orElse(null);
		
		moduleRepo.delete(m);
		
	}


	@Override
	public Module UpdateModule(Module m) {
		return moduleRepo.save(m);
	}


	
	
	
	
	
	
	
	
	
	
	
//	@Scheduled(cron="0 0 0 * * *")
//	public List<Module> getTraitementModule() {
//		
		
		
		
		
//		List<Condidacy> lcm =new ArrayList<>();
//		int size=0;
//		List<Module> m= moduleRepo.getModuleAutomatique();
//		for(Module i: m){
//		   List<Condidacy> lc= (List<Condidacy>)condidacyRepo.findAll();
//		 for(Condidacy j:lc){
//			 if(j.getModule()==i)
//				 lcm.add(j);
//			 }
//		     
//		  for(Condidacy k:lcm){
//			int size1=k.getCandidat().getCv().getSkills().size();
//			if(size<size1){
//				size=size1;
//			
//			
//				
//			}
//			
		
		
			
//		  }
		 
		  
		 
//		}
//		
//		return null;
//		
//	}


	

}