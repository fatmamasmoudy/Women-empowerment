package tn.esprit.spring.controllers;

import java.util.List;

import javax.mail.MessagingException;
import javax.websocket.server.PathParam;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Condidacy;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.services.ICondidacyService;
import tn.esprit.spring.services.IUserService;


@RestController
@RequestMapping("/condidacy")
public class CondidacyRestController {
	
	@Autowired
	ICondidacyService condidacyService;
	@Autowired
	IUserService userService;
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtUtils jwtUtils;
	
	
	
	@PostMapping("/add-condidacy")
	
	public ResponseEntity<?> addCondidacy(@RequestHeader (name="Authorization") String token,@RequestBody Condidacy c){
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
		condidacyService.addCandidacy(c,user.getId());
		return ResponseEntity.ok(c);
	}
	
	
	
	
//	@GetMapping("/email/{id}")
//	public User sendEmail(@RequestParam("id")long idUser,@RequestParam("body")String body,@RequestParam("subject")String subject,@RequestParam("attachment")String attachment) throws MessagingException{
//	User user= userService.findById(idUser);
//	EmailSenderService mail=new EmailSenderService();
//	mail.sendMailWithAttachment(user.getEmail(), body, subject, attachment);
//	
//		return user;
//		
//		
//	}
	
	@GetMapping("/retrieve-condidacy")
	public List<Condidacy> AfficherCondidacy(){
		return condidacyService.listCondidacy();
		
	}
	
	@PutMapping("/modifier-condidacy")
	public Condidacy ModifierCondidacy(@RequestBody Condidacy c){
		return condidacyService.UpdateCondidacy(c);
	
	}
	
   @DeleteMapping("/delete-condidacy/{id}")
    public void supprimerCondidacy(@PathVariable("id") int id){
	   
	   condidacyService.DeleteCondidacy(id);
	
}
   
}