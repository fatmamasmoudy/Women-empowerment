package tn.esprit.spring.controllers;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.repository.SubjectRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.services.*;
 @RestController
@RequestMapping("/Subject")
public class SubjectRestController {
	@Autowired
	SubjectServiceImpli subjectService;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	
	// http://localhost:8083/women/Subject/All 
	@GetMapping("/All")
	public List<Subject> getSubjects() {
	List<Subject> listSubject = subjectService.retrieveAllSubject();
	return listSubject;
}

	// http://localhost:8083/women/Subject/ShowSubject/{Subject-id}
	@GetMapping("/ShowSubject/{Subject-id}")
	public Subject retrieveSubject(@PathVariable("Subject-id") int id) {
		Subject S = subjectService.retrieveSubject(id);
	

		return S;
	}
	// http://localhost:8083/women/Subject/AddSubject

	@PostMapping("/AddSubject")
	public ResponseEntity<?>  addSubject(@RequestHeader (name="Authorization") String token,@RequestBody  Subject s) {
		if(token==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
		}
		String email;
		try{
			token=token.replace("Bearer ", "");
			email = jwtUtils.getUserNameFromJwtToken(token);
		if(email==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		}catch(Exception e){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		
		User user=userRepository.findByEmail(email).get();
	 subjectService.addSubject(s,user.getId() );
		return ResponseEntity.ok(s);


	}
	// http://localhost:8083/women/Subject/DeleteSubject/{Subject-id}

	@DeleteMapping("/DeleteSubject/{Subject-id}")
	public void DeleteSubject(@PathVariable("Subject-id") int id) {
		subjectService.deleteSubject(id);
	}
	// http://localhost:8083/women/Subject/UpdateSubject

	@PutMapping("/UpdateSubject")
	public Subject updateSubject(@RequestBody Subject subject) {
	return subjectService.updateSubject(subject);
	}
	// http://localhost:8083/women/Subject/news
	@GetMapping("/news")
	public  List<Subject>  retrieveSubjectByDate(){
		List<Subject> listNews = subjectService.retrieveSubjectByDate();
	return listNews;
	}
	
 }