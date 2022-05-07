package tn.esprit.spring.services;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Domain;
import tn.esprit.spring.entities.IntrestedBy;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.SubjectRepository;
import tn.esprit.spring.repository.TagRepo;
@Service
public class TagsServiceImpli implements ITagsService {

	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	TagRepo tagsRepo;


	    
		public IntrestedBy addTag(Subject S) {
			
			IntrestedBy I = new IntrestedBy();
			Domain coiffure = Domain.Coiffure;
			Domain couture = Domain.Couture;
			Domain cuisine = Domain.Cuisine;
			Domain entreprenariat = Domain.Entreprenariat;
			Domain architecture = Domain.Architacture;
			Domain marketing = Domain.Marketing;
			Domain sante = Domain.Sante;
			Domain economie = Domain.Economie;
			Domain communication = Domain.Communication;
			Domain mecanique = Domain.Mecanique;
			Domain informatique = Domain.Informatique;
			User U = S.getUser();
			String Description = S.getDescription();
			I.setUser(U);
 			I.setCoiffureValue(TagsFilterMakeup.getTagsText(Description));
 			if ( I.getCoiffureValue() !=0) {
 				S.setDomaine(coiffure);
 			}
 			I.setCoutureValue(TagsFilterCouture.getTagsText(Description));
 			if ( I.getCoutureValue() !=0) {
 				S.setDomaine(couture);
 			}
 			I.setCuisineValue(TagsFilterCouture.getTagsText(Description));
 			if ( I.getCuisineValue() !=0) {
 				S.setDomaine(cuisine);
 			}
 			I.setEntreprenariatValue(TagsFilterEntreprenariat.getTagsText(Description));
 			if ( I.getEntreprenariatValue() !=0) {
 				S.setDomaine(entreprenariat);
 			}
 			I.setArchitectureValue(TagsFilterArchitecture.getTagsText(Description));
 			if ( I.getArchitectureValue() !=0) {
 				S.setDomaine(architecture);
 			}
 		
 			I.setMarketingValue(TagsFilterMarketing.getTagsText(Description));
 			if ( I.getMarketingValue() !=0) {
 				S.setDomaine(marketing);
 			}
 			I.setSanteValue(TagsFilterSante.getTagsText(Description));
 			if ( I.getSanteValue() !=0) {
 				S.setDomaine(sante);
 			}
 			I.setEconomieValue(TagsFilterEconomie.getTagsText(Description));
 			if ( I.getEconomieValue() !=0) {
 				S.setDomaine(economie);
 			}
 			I.setCommunicationValue(TagsFilterCommunication.getTagsText(Description));
 			if ( I.getCommunicationValue() !=0) {
 				S.setDomaine(communication);
 			}
 			I.setMecaniqueValue(TagsFilterMecanique.getTagsText(Description));
 			if ( I.getMecaniqueValue() !=0) {
 				S.setDomaine(mecanique);
 			}
 			I.setInformatiqueValue(TagsFilterInformatique.getTagsText(Description));
 			if ( I.getInformatiqueValue() !=0) {
 				S.setDomaine(informatique);
 			}
 			I.setCuisineValue(TagsFilterKitchen.getTagsText(Description));
 			if ( I.getCuisineValue() !=0) {
 				S.setDomaine(cuisine);
 			}
 			
			 return tagsRepo.save(I);
		}
		
		public List<IntrestedBy> retrieveAllIntres() {
			return (List<IntrestedBy>) tagsRepo.findAll();
		}
		
	    

	}
	

	
	
	
	 

