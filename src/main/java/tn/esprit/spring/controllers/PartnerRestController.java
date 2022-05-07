package tn.esprit.spring.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.JwtResponse;
import tn.esprit.spring.payload.LoginRequest;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.payload.SignUpPartnerRequest;
import tn.esprit.spring.payload.SignUpRequest;
import tn.esprit.spring.repository.PartnerRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.security.PartnerDetailsImpl;
import tn.esprit.spring.security.UserDetailsImpl;
import tn.esprit.spring.services.IPartnerService;

import tn.esprit.spring.entities.Condidacy;
import tn.esprit.spring.entities.Note;
import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.JwtResponse;
import tn.esprit.spring.payload.LoginRequest;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.payload.SignUpPartnerRequest;
import tn.esprit.spring.payload.SignUpRequest;
import tn.esprit.spring.repository.PartnerRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.security.PartnerDetailsImpl;
import tn.esprit.spring.security.UserDetailsImpl;
import tn.esprit.spring.services.FileUploadUtil;
import tn.esprit.spring.services.IPartnerService;

@RestController
@RequestMapping("/partner")
public class PartnerRestController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PartnerRepository partnerRepository;
	@Autowired
	IPartnerService partnerService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	

	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerPartner(@Valid @RequestBody SignUpPartnerRequest signUpRequest) {


		if (partnerRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
	
		// Create new user's account
		Partner partner = new Partner(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
				encoder.encode(signUpRequest.getPasswordConfirm()), signUpRequest.getNote(),
				signUpRequest.getName(),new Date(),signUpRequest.getType()
				
				);

		
		partnerService.addPartner(partner);
		
		return ResponseEntity.ok(new MessageResponse("Partner registered successfully!"));
	}

	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try{
				Partner u=partnerRepository.findByEmail(loginRequest.getEmail()).orElse(null);
				
				
				if(u.isDesactivate()){
					return ResponseEntity.badRequest().body(new MessageResponse("Error: This account is desactivate"));
				}
				
				
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtTokenPartner(authentication);

				PartnerDetailsImpl userDetails = (PartnerDetailsImpl) authentication.getPrincipal();
				Partner user=partnerService.retrievePartner(userDetails.getId());
				user.setAccessToken(jwt);
				user.setCounterLogin(0);
				
				partnerService.updatePartner(user);
				return ResponseEntity.ok(
						new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
		}catch (Exception e) {
			Partner user=partnerRepository.findByEmail(loginRequest.getEmail()).orElse(null);
			if(user!=null){
				int compt=user.getCounterLogin();
				if(compt<2){
				user.setCounterLogin(++compt);
				partnerService.updatePartner(user);
				return ResponseEntity.badRequest().body(new MessageResponse( "Error: "+ compt+" tentative(s),you have three, please try again"));
				}
				else{
					user.setDesactivate(true);
					partnerService.updatePartner(user);
					return ResponseEntity.badRequest().body(new MessageResponse("Error:  account is desactivate, the clientistrator will see your connection tentavie and send you a mail with the new coordonates "));
				}
				
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Error:  try to push a true username and password"));
		}	
		}


	@PutMapping("/disconnect/{id}")
	public ResponseEntity<?> disconnectPartner(@PathVariable("id") long id) {
		Partner u=partnerService.retrievePartner(id);
		u.setAccessToken("");
		partnerService.updatePartner(u);
		return ResponseEntity.ok(new MessageResponse("Partner disconnected successfully!"));
	}
	
	
	//Crud
	
		@DeleteMapping("/delete-profil/{id}")
		public ResponseEntity<?> deleteprofil(@PathVariable("id") long id) {		
			 partnerService.deletePartner(id);
			 return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
		}
		
		//http://localhost:8095/women/user/get-allUsers
		@GetMapping("/get-allPartners")
		public List<Partner> getallUsers() {		
			return partnerService.retrieveAllPartners();
		}
		//http://localhost:8095/women/user/get-UsersById/{5}
		@GetMapping("/get-PartnersById/{id}")
		public Partner getUserById(@PathVariable("id") long id) {		
			return partnerService.retrievePartner(id);
		}
		
		
		@GetMapping("/retrieve-partner/{address}")
		public List<Partner> FilterPartner(@PathVariable("address") String address){
			return partnerService.FilterPartner(address);
			
		}
		
		
		
		@RequestMapping("/search/{keyword}")
		public List<Partner> search(@PathVariable("keyword") String keyword){
			return partnerService.SearchPartner(keyword);
		}
		
		
		@GetMapping("/stat/{idPartner}")
		public List<Condidacy> Statistique(@PathVariable("idPartner") long idPartner){
			return partnerService.StatistiqueUser(idPartner);
			
		}
		
		
		@PostMapping("/add_logo")
		public  ResponseEntity<?> addlogo(@RequestParam("logo") MultipartFile logo,@RequestHeader (name="Authorization") String token){
			
			
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
			
			
			Partner partner=partnerRepository.findByEmail(email).get();
			
			
			String fileName = StringUtils.cleanPath(logo.getOriginalFilename());
	        
			partner.setLogo(fileName);
			
			partnerService.addPartner(partner);
			
			String uploadDir = "uploads/" + partner.getId();
			 
	        try {
				FileUploadUtil.saveFile(uploadDir, fileName, logo);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return ResponseEntity.ok(partner);
			
			
		}
		
		
		
		
		@GetMapping("/rate/{idPartner}/{rating}")
		public float Rating(@PathVariable("idPartner") long idPartner,@PathVariable("rating") float rating){
			
			return partnerService.RatingPartner(rating,idPartner );
			
		}
		
		
		@GetMapping("/suggestion/{idUser}")
		public List<Partner> Suggestion(@PathVariable("idUser") long idUser){
			return partnerService.Suggestion(idUser);
			
		}
	
}
