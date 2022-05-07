package tn.esprit.spring.controllers;

import java.util.List;

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

import tn.esprit.spring.entities.Condidacy;
import tn.esprit.spring.entities.Module;
import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.repository.PartnerRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.services.IModuleService;
import tn.esprit.spring.services.IPartnerService;

@RestController
@RequestMapping("/module")
public class ModuleRestController {

	@Autowired
	IModuleService moduleService;
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/add-module/{id}")
	public void addCondidacy(@RequestBody Module m,@PathVariable("id") long idPatner){
		
		 moduleService.ajouterModule(m,idPatner);
		 
	}
	
	@GetMapping("/retrieve-module")
	public List<Module> AfficherModule(){
		return moduleService.listerModules();
		
	}
	
	@PutMapping("/modifier-module")
	public Module ModifierModule(@RequestBody Module m){
		return moduleService.UpdateModule(m);
	
	}
	
	
	 @DeleteMapping("/delete-module/{id}")
	    public void supprimerModule(@PathVariable("id") int id){
		   
		 moduleService.SupprimerModule(id);
		
	}
	
	
	
	
	
	
	
	
	
}