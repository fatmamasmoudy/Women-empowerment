package tn.esprit.spring.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.services.DictionnaireReclamation;
import tn.esprit.spring.services.IReclamationService;
import tn.esprit.spring.services.IUserService;

@RestController
@RequestMapping("/Reclamation")
public class ReclamationRestController {
	
	@Autowired
	IReclamationService reclamationService;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepository userrepository;
	
	@PostMapping("/add")
	public ResponseEntity<?> addreclamation(@RequestHeader (name="Authorization") String token ,@RequestBody Reclamation reclamation){
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
		
		User user=userrepository.findByEmail(email).get();
		reclamation.setCreated_at(new Date());
		reclamation.setUsers(user);
		reclamationService.addReclamation(reclamation);
		 
		 int i=0;
		 
		 for (String k : DictionnaireReclamation.getDecisions().keySet()) { 
	         if(reclamation.getText().toLowerCase().contains(k.toLowerCase()))
	         {
	        	 reclamation.setDecision(DictionnaireReclamation.getDecisions().get(k));
	        	 reclamationService.updateReclamation(reclamation);
	        	 SendMail.email(DictionnaireReclamation.getDecisions().get(k), user.getEmail(), user.getFirstName());
	        	 i++;
	        	 break;
	         }
			if(i>0) break;
	        } 
		 if(i>0)
			 return ResponseEntity.ok(new MessageResponse("Reclamation is treated, you will receive an email automaticaly"));
		 else
			 return ResponseEntity.ok(new MessageResponse("Veuillez contacter l'adminstration par email:fatma@esprit.tn"));
		
	}
	
	//http://localhost:8095/women/user/delete-reclamation
	@DeleteMapping("/delete-reclamation/{id}")
	public ResponseEntity<?> deletereclamation(@PathVariable("id") long id) {		
		 reclamationService.deleteReclamation(id);
		 return ResponseEntity.ok(new MessageResponse("Reclamation deleted successfully!"));
	}
	
	//http://localhost:8095/women/user/get-allUsers
	@GetMapping("/get-allReclamation")
	public List<Reclamation> getallReclamation() {		
		return reclamationService.retrieveAllReclamations();
	}
	//http://localhost:8095/women/user/get-UsersById/{5}
	@GetMapping("/get-ReclamationId/{id}")
	public Reclamation getReclamationById(@PathVariable("id") long id) {		
		return reclamationService.retrieveReclamation(id);
	}

	
	

}
