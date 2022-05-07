package tn.esprit.spring.services;

import java.util.List;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Advertisement;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.AdRepository;
import tn.esprit.spring.repository.UserRepository;
 
@Service

public class AdServiceImpli implements IAdService{
	@Autowired
	AdRepository adRepository;
	@Autowired
	SubjectServiceImpli subjectService;
	@Autowired
	UserServiceImpl UserService;
	@Autowired
	 UserRepository userRepository;
	
	
	@Override
	public List<Advertisement> retrieveAllAdvertisementByIntestedBy() {
		List<Subject> listSubject = subjectService.retrieveAllSubject();
		List<Advertisement>  ListAllAds = (List<Advertisement>) adRepository.findAll();
	
		for (Advertisement Ads : ListAllAds) {
			 for (Subject S : listSubject) {

			if((S.getUser().getId() == Ads.getUser().getId())&&(Ads.getDomain()==S.getDomaine())){
				
					return (List<Advertisement>) adRepository.findByDomain(S.getDomaine());
				
		 }
		}
 	 }	
 	 
 	 
		
		
		return ListAllAds;
	}
	


	public Advertisement addAdvertisement(Advertisement A ,  long UserId)   {
		User U = userRepository.findById(UserId);

		Facebook facebook = new FacebookTemplate("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBaGxlbS5iZW5mcmFkakBlc3ByaXQudG4iLCJpYXQiOjE2NDY4Mjc5OTgsImV4cCI6MTY0NjkxNDM5OH0.3tHDEznYyMPOFzbnMd-Imr85q4AfCTAWOt2XQFYhpZuvtdyL0vftFGWoAZiZkQb8W9W6-LRMEKEN5q6K8c95Ew");
        if(facebook.isAuthorized()){
        	String Add = A.getNameAd();
            facebook.feedOperations().updateStatus(Add) ;
            }
        A.setUser(U);
        return adRepository.save(A);

	}



	@Override
	public void deleteAdvertisement(int id) {
		adRepository.deleteById(id);
	}

	@Override
	public Advertisement updateAdvertisement(Advertisement A) {
		return adRepository.save(A);

	}

	@Override
	public Advertisement retrieveAdvertisement(int id) {
		return adRepository.findById(id).orElse(null);	

	}



	@Override
	public List<Advertisement> retrieveAllAdvertisement() {
		List<Advertisement>  ListAllAds = (List<Advertisement>) adRepository.findAll();
		
		return ListAllAds;
	}

}
