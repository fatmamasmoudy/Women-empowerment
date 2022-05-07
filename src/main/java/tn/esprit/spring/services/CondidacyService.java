package tn.esprit.spring.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Condidacy;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.Skill;
import tn.esprit.spring.entities.State;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.CondidacyRepository;
import tn.esprit.spring.repository.ModuleRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class CondidacyService implements ICondidacyService {
	

	@Autowired
	CondidacyRepository condidacyRepo;
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	ModuleRepository moduleRepo;
	@Autowired
    private JavaMailSender javaMailSender;
	
	
	
	 String listeDictionnaire;
	 
		
	
	@Override
	 public Condidacy  addCandidacy(Condidacy c,long idUser){
		 Module m=moduleRepo.findById(c.getModule().getId()).orElse(null);
		  User u= userRepo.findById(idUser);
		  c.setCandidat(u);
			  
		  if(m.getCapacity()> m.getNbreInscription() && m.getPartner().isDisponible()){
				 
				 condidacyRepo.save(c);
				  
				 
			  }		 
		  return c;
		}
	

	
	@Override
	public List<Condidacy> listCondidacy() {
		
		return (List<Condidacy>) condidacyRepo.findAll();
	}

	@Override
	public Condidacy UpdateCondidacy(Condidacy c) {
		
		return condidacyRepo.save(c);
	}

	@Override
	public void DeleteCondidacy(int id) {

		Condidacy c=condidacyRepo.findById(id).orElse(null);
		condidacyRepo.delete(c);
	}


	@Override
	public void TraitementCondidacy() {
		
		
		
	}


	
	
	
	@EventListener(ApplicationReadyEvent.class)
	@Scheduled(cron="*/10 * * * * *")
	  
	//@Scheduled(cron="*/10 * * * * *")
	
	@Override
	public void ModuleAutomatique() throws MessagingException {
	
		List<Condidacy> liste = condidacyRepo.getModuleAutomatique();
		
		 
		for (Condidacy condidacy : liste) {
			
			try {
				
	  if(condidacy.getModule().getName().toString().equals("Informatique")){
		listeDictionnaire = loadFile(new File("C:/Users/DELL/Desktop/Esprit/spring/sts-bundle/sts-3.8.4.RELEASE/workspace-sts/PIDEV/src/main/resources/DictionnaireDomain/Informatique.txt"));
		}
	  else if(condidacy.getModule().getName().toString().equals("Economique")){
		listeDictionnaire = loadFile(new File("C:/Users/DELL/Desktop/Esprit/spring/sts-bundle/sts-3.8.4.RELEASE/workspace-sts/PIDEV/src/main/resources/DictionnaireDomain/Economique.txt"));
		}
				
	  else if(condidacy.getModule().getName().toString().equals("Medecine")){
		listeDictionnaire = loadFile(new File("C:/Users/DELL/Desktop/Esprit/spring/sts-bundle/sts-3.8.4.RELEASE/workspace-sts/PIDEV/src/main/resources/DictionnaireDomain/Medecine.txt"));
		}
	  else if(condidacy.getModule().getName().toString().equals("Mecanique")){
		listeDictionnaire = loadFile(new File("C:/Users/DELL/Desktop/Esprit/spring/sts-bundle/sts-3.8.4.RELEASE/workspace-sts/PIDEV/src/main/resources/DictionnaireDomain/Mecanique.txt"));
		}
				
		
				
				
				
				
				
				
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int occurence=0;
			
			List<Skill> skills=condidacy.getCandidat().getCv().getSkills();
			
			
			
			for (Skill skill : skills) {
				if(listeDictionnaire.toUpperCase().contains(skill.getDesignation().toUpperCase()))
					{
						occurence ++;
					
					}
				condidacy.setScore(occurence);
				
					
				
			
				
//				try {
//				 //if(dictionnaire.traitement(condidacy,skill)){
//					 
//					 
//					 occurence++;
//				 }
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
			
			
			}
			
			
			
		
		System.out.println("list of condidancy score "+occurence+"list of condidancy id "+condidacy.getId());

		

		}
		
	   
//   CondidacyComparator cc=new CondidacyComparator();
//    Collections.sort(liste, cc);
	
		
		
		
		
		
		
		
		
		for (Condidacy condidacy1 : liste) {
			int capacite=condidacy1.getModule().getCapacity();
			int nb=condidacy1.getModule().getNbreInscription();
		
			
			if(capacite > nb){
				System.out.println("ok bravo");
				condidacy1.setState(State.Accepted);
			  
				Module m=condidacy1.getModule();
				m.setNbreInscription(nb+1);
				condidacyRepo.save(condidacy1);
				moduleRepo.save(m);
				System.out.println(condidacy1.getState());
				String mail=condidacy1.getCandidat().getEmail();
				String universityName=condidacy1.getModule().getPartner().getName();
			
				
		sendMailWithAttachment(condidacy1.getCandidat(),condidacy1.getModule().getPartner()) ;
					

			}
     }
	
		
	}	
			
			
			
			
			
			
			
			
	

	
	 public void sendMailWithAttachment(User user, Partner partner) throws MessagingException {
	    	String html="Dear <strong>[[name]]</strong>,<br>"
		            + "<strong>Congradulation your condidacy is accepted:</strong><br>"
		            + "<h3 color='red' >[[establishment]]</h3>"
		            + "Thank you,<br>";
			
			String subject="Condidacy Acceptation";
			
			html = html.replace("[[name]]", user.getFirstName());
			html = html.replace("[[establishment]]", partner.getName());
			
	        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
	        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
	        mimeMessageHelper.setFrom("sahargharad@gmail.com");
	        mimeMessageHelper.setTo(user.getEmail());
	        //mimeMessageHelper.setText(body);
	        
	      
	        mimeMessageHelper.setText(html, true);
	        
	        mimeMessageHelper.setSubject(subject);
	        
	        File attachment = new File("C:/Users/DELL/Desktop/13-134869_m.jpg");
			
	
	mimeMessageHelper.addAttachment("partner",attachment);
	javaMailSender.send(mimeMessage);
	System.out.println("Mail with attachment sent successfully..");


	}
	
	
	
	
	
		
		
		public String loadFile(File file)throws Exception{
			String s = "";
			String st;
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null)

	        s=s+st;

			return s;
			
		}
	
	
	}