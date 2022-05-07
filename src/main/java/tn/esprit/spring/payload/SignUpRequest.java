package tn.esprit.spring.payload;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.IntrestedBy;
import tn.esprit.spring.entities.Meeting;
import tn.esprit.spring.entities.Reclamation;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	private String email;

	private String password;
	private String passwordConfirm;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String sexe;
	private String civilState;
	private String address;
	private int postalCode;
	private int phoneNumber;
	private String nationality;
	private String job;
	private String studyLevel;
	private String status;
	private float monthlyUncome;
	private int nbPersFamily;
	private int nbStudentsInFamily;
	private String favory;	
	
		
	
	

}
