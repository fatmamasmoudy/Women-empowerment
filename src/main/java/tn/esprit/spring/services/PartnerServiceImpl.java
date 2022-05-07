package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Condidacy;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.CondidacyRepository;
import tn.esprit.spring.repository.ModuleRepository;
import tn.esprit.spring.repository.PartnerRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class PartnerServiceImpl implements IPartnerService{
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@Autowired
	CondidacyRepository condidacyRepo;
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ModuleRepository moduleRepo;
	
	
	@Override
	public List<Partner> retrieveAllPartners() {
		return (List<Partner>) partnerRepository.findAll();
	}
	
	@Override
	public Partner addPartner(Partner u) {
		partnerRepository.save(u);
		return u;
	}

	@Override
	public void deletePartner (long id) {
		partnerRepository.deleteById(id);
	}

	@Override
	public Partner updatePartner(Partner u) {
		partnerRepository.save(u);
		return u;
	}

	@Override
	public Partner findById(long id) {
		
		return partnerRepository.findById(id);
	}
	
	@Override
	public Partner retrievePartner(long id) {
		Partner u = partnerRepository.findById(id);
		return u;
	}
	
	
	@Override
	public List<Partner> FilterPartner(String address) {
		List<Partner> l=partnerRepository.FilterPartner(address);
		for (Partner partner : l) {
			//System.out.println("test test");
			System.out.println(partner.getAddress());
		}
		return partnerRepository.FilterPartner(address);
		
		
	}
	
	@Override
	public List<Partner> SearchPartner(String keyword) {
		
		if (keyword != null) {
            return partnerRepository.SearchPartner(keyword);
        }
          return (List<Partner>) partnerRepository.findAll();
        }

	
	@Override
	public List<Condidacy> StatistiqueUser(long idPartner) {
		List<Module> modules=partnerRepository.GetModule(idPartner);
		List<Condidacy> condidacy=new ArrayList<Condidacy>();
		for (Module module2 : modules) {
		  condidacy.addAll(condidacyRepo.findByModule(module2));
			
		}
		

		
	  return condidacy;
	}
	
	@Override
	public float RatingPartner(float rating,long idPartner) {
		Partner p=partnerRepository.findById(idPartner);
		 float rate=p.getNote();
		 if(rate<0){
			 p.setNote(rating);
			 partnerRepository.save(p);
			 return p.getNote();
		 }
		 else
		 rate=(rate+rating)/2;
		 p.setNote(rate);
		 partnerRepository.save(p);
		 return rate;
	}

	@Override
	public List<Partner> Suggestion(long id) {
		
		List<Condidacy> condidacies=condidacyRepo.findCondidacyUser(id);
		List<Module> modules= (List<Module>) moduleRepo.findAll();
		List<Partner> partners=new ArrayList<>();

		
			
		for (Condidacy condidacy : condidacies) {
			for (Module module : modules) {
				if(condidacy.getModule().getName().equals(module.getName())){
					partners.add(module.getPartner());
				}
				
			}
			
		}
		
		return partners;
	}
	

}