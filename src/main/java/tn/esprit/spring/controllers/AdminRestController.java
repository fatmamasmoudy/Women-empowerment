package tn.esprit.spring.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Admin;
import tn.esprit.spring.entities.Partner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.JwtResponse;
import tn.esprit.spring.payload.LoginRequest;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.repository.AdminRepository;
import tn.esprit.spring.security.AdminDetailsImpl;
import tn.esprit.spring.security.JwtUtils;

import tn.esprit.spring.services.*;


@RestController
@RequestMapping("/admin")
public class AdminRestController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	IAdminService adminService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IPartnerService partnerService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	
	//http://localhost:8095/women/admin/signin
		@PostMapping("/signin")
		public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {
			try{
					
					Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					//générer token
					String jwt = jwtUtils.generateJwtTokenAdmin(authentication);

					//récupérer l utilisateur
					AdminDetailsImpl adminDetails = (AdminDetailsImpl) authentication.getPrincipal();
					Admin admin=adminService.retrieveAdmin(adminDetails.getId());
					
					
					
					return ResponseEntity.ok(
							new JwtResponse(jwt, adminDetails.getId(), adminDetails.getUsername(), adminDetails.getEmail()));
			}catch (Exception e) {
				
				return ResponseEntity.badRequest().body(new MessageResponse("Error:  try to push a true username and password"));
			}	
			}


		@PutMapping("/User-activate-disactivate/{id}/{status}")
		public ResponseEntity<?> activerdisactiverUser(@PathVariable("id") long id, @PathVariable("status") int status) {
		User u=	userService.retrieveUser(id);
		if(status==1){
			u.setDesactivate(false);
			u.setCounterLogin(0);
			userService.updateUser(u);
			return ResponseEntity.ok("User is activate");
		}else{
			u.setDesactivate(true);
			u.setCounterLogin(0);
			userService.updateUser(u);
			return ResponseEntity.ok("User is disactivate");
		}
		}
		
		@PutMapping("/Partner-activate-disactivate/{id}/{status}")
		public ResponseEntity<?> activerdisactiverPartner(@PathVariable("id") long id, @PathVariable("status") int status) {
		Partner u=	partnerService.retrievePartner(id);
		if(status==1){
			u.setDesactivate(false);
			u.setCounterLogin(0);
			partnerService.updatePartner(u);
			return ResponseEntity.ok("Partner is activate");
		}else{
			u.setDesactivate(true);
			u.setCounterLogin(0);
			partnerService.updatePartner(u);
			return ResponseEntity.ok("Partner is disactivate");
		}
		
		}
}



