package tn.esprit.spring.services;

 
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.IntrestedBy;
import tn.esprit.spring.entities.Reaction;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.SubjectRepository;
import tn.esprit.spring.repository.UserRepository;


@Service

public class SubjectServiceImpli implements ISubjectService {
	@Autowired
	EmailSenderService service;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TagsServiceImpli tag;
	@Autowired
	ReactionServiceImpl reactionService;


	@Override
	public List<Subject> retrieveAllSubject() {
		return (List<Subject>) subjectRepository.findAll();

	}
	
	




	@EventListener(ApplicationReadyEvent.class)
	public void send(){
	service.sendMail("ahlem.benfradj@esprit.tn","New Subject is Added Check this out !!", "New Subject is Added Check this out !!");
	}





	public void deleteSubject(int id) {
		
		subjectRepository.deleteById(id);

	}

	@Override
	public Subject updateSubject(Subject S) {
		String Description = S.getDescription();
		S.setDescription(BadWordFilter.getCensoredText(Description));
		Date currentUtilDate = new Date();
		S.setCreatedAt(null);
		S.setUpdatedAt(currentUtilDate);

		
		return subjectRepository.save(S);
	}

	@Override
	public Subject retrieveSubject(int id) {
		
		Subject S = subjectRepository.findById(id).orElse(null);
		int nb = S.getNbVue() + 1;
		S.setNbVue(nb);
		subjectRepository.save(S);
		return S;

	}


	@Override
	public List<Subject> retrieveSubjectByDate() {
		return (List<Subject>) subjectRepository.retrieveClientsByDateSql();
	}


@Scheduled(cron = "0 0 0 * * ? ")
	public void bandedSubject() {
	List<Subject> ListSu = (List<Subject>) subjectRepository.findAll();
	for (Subject S : ListSu) {
		if ((S.getNbLike() == 0) && (S.getNbDislike() == 0)) {
			subjectRepository.delete(S);
			System.out.println("Subject is deleted cuz there's no interactions");
		}

	}
}
	
	public Subject addSubject(Subject S, long UserId) {
			
			List<Subject> ListSub = subjectRepository.exists(S.getDescription(), S.getName());
		System.out.println(ListSub);
		if (!ListSub.isEmpty()) {
			System.out.println("already exist");
			}
			else {
				List <Subject> reactedSub= (List<Subject>) subjectRepository.findAll();
				

			User U = userRepository.findById(UserId);
			String Description = S.getDescription();
			S.setDescription(BadWordFilter.getCensoredText(Description));
			Date currentUtilDate = new Date();
			S.setCreatedAt(currentUtilDate);
			S.setUpdatedAt(null);
			S.setNbLike(0);
			S.setNbDislike(0);
			S.setNbVue(0);	
			S.setUser(U);
 			tag.addTag(S);
 			send();
			subjectRepository.save(S);
		}

		return S;
	}
public Subject retrieveSubjectNByInteret(int id) {
	 List <Reaction> Reactions = (List<Reaction>) reactionService.reactionRepo.findAll();
	 for (Reaction reaction : Reactions) {
		 int IdR = reaction.getSubject().getId();
		if (IdR == id){
	
	
		Subject S = subjectRepository.findById(id).orElse(null);
		int nb = S.getNbVue() + 1;
		S.setNbVue(nb);
		subjectRepository.save(S);
		return S;
		}
	 }
	 return null;
}
}