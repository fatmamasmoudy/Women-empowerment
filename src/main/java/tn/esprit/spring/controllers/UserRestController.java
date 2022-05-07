package tn.esprit.spring.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import tn.esprit.spring.security.JwtUtils;

import tn.esprit.spring.payload.JwtResponse;
import tn.esprit.spring.payload.LoginRequest;
import tn.esprit.spring.security.UserDetailsImpl;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.payload.SignUpRequest;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	@Autowired
	IUserService userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {


		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
	
		// Create new user's account
		User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
				encoder.encode(signUpRequest.getPasswordConfirm()), signUpRequest.getFirstName(), signUpRequest.getLastName(),
				signUpRequest.getDateOfBirth(), signUpRequest.getSexe(),signUpRequest.getCivilState(),
				signUpRequest.getAddress(), signUpRequest.getPostalCode(), signUpRequest.getPhoneNumber(),
				signUpRequest.getNationality(), signUpRequest.getJob(), signUpRequest.getStudyLevel(),
				signUpRequest.getStatus(), signUpRequest.getMonthlyUncome(), signUpRequest.getNbPersFamily(),
				signUpRequest.getNbStudentsInFamily(), signUpRequest.getFavory()
				);

		
		userService.addUser(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	//http://localhost:8095/women/user/signin
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try{
				User u=userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
				
				
				if(u.isDesactivate()){
					return ResponseEntity.badRequest().body(new MessageResponse("Error: This account is desactivate"));
				}
				
				
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				//générer token
				String jwt = jwtUtils.generateJwtToken(authentication);

				//récupérer l utilisateur
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				User user=userService.retrieveUser(userDetails.getId());
				user.setAccessToken(jwt);
				user.setCounterLogin(0);
				
				userService.updateUser(user);
				return ResponseEntity.ok(
						new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
		}catch (Exception e) {
			User user=userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
			if(user!=null){
				int compt=user.getCounterLogin();
				if(compt<2){
				user.setCounterLogin(++compt);
				userService.updateUser(user);
				return ResponseEntity.badRequest().body(new MessageResponse( "Error: "+ compt+" tentative(s),you have three, please try again"));
				}
				else{
					user.setDesactivate(true);
					userService.updateUser(user);
					return ResponseEntity.badRequest().body(new MessageResponse("Error:  account is desactivate, the clientistrator will see your connection tentavie and send you a mail with the new coordonates "));
				}
				
			}
			return ResponseEntity.badRequest().body(new MessageResponse("Error:  try to push a true username and password"));
		}	
		}


	@PutMapping("/disconnect/{id}")
	public ResponseEntity<?> disconnectUser(@PathVariable("id") long id) {
		User u=userService.retrieveUser(id);
		u.setAccessToken("");
		userService.updateUser(u);
		return ResponseEntity.ok(new MessageResponse("User disconnected successfully!"));
	}
	
	
	
	//Crud
	
	@DeleteMapping("/delete-profil/{id}")
	public ResponseEntity<?> deleteprofil(@PathVariable("id") long id) {		
		 userService.deleteUser(id);
		 return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
	}
	
	//http://localhost:8095/women/user/get-allUsers
	@GetMapping("/get-allUsers")
	public List<User> getallUsers() {		
		return userService.retrieveAllUsers();
	}
	//http://localhost:8095/women/user/get-UsersById/{5}
	@GetMapping("/get-UsersById/{id}")
	public User getUserById(@PathVariable("id") long id) {		
		return userService.retrieveUser(id);
	}
	////// ********************************
	//****************************************
	//************************************
	//          ahmed
	
}
