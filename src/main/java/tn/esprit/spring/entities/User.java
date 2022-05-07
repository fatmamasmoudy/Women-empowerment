package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique=true, length=255)
	private String email;
	@Column(nullable = false, unique=true, length=255)
	private String password;
	@Column(nullable = false, unique=true, length=255)
	private String passwordConfirm;
	private String firstName;
	private String lastName;
	@Temporal (TemporalType.DATE)
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
	private boolean isDesactivate;
	private int counterLogin;
	private String accessToken;
	@JsonIgnore
	@Column(nullable = true,  length=255)
    private String resetToken;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="users")
	private Set<Reclamation> reclamations;
	

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<Meeting> meetings;
	
	@JsonIgnore
	@OneToOne
	private IntrestedBy intrestedBy;
	
	@JsonIgnore
	@OneToMany(mappedBy = "candidat", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Condidacy> condidaciesC;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Event> events;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	public List<Don> dons;
	
	
	@JsonIgnore
	@OneToOne(mappedBy="user")
	private CV cv;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy="learners")
	public Set<Training> trainings;
	
	@OneToMany(mappedBy = "user")
	private List<Subject> subjects;
	
	@OneToMany(mappedBy = "user")
	private List<Advertisement> ads;
	
	
	
	
	
	
	
	public User(String email, String password, String passwordConfirm, String firstName, String lastName,
			Date dateOfBirth, String sexe, String civilState, String address, int postalCode, int phoneNumber,
			String nationality, String job, String studyLevel, String status, float monthlyUncome, int nbPersFamily,
			int nbStudentsInFamily, String favory) {
		super();
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.sexe = sexe;
		this.civilState = civilState;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.nationality = nationality;
		this.job = job;
		this.studyLevel = studyLevel;
		this.status = status;
		this.monthlyUncome = monthlyUncome;
		this.nbPersFamily = nbPersFamily;
		this.nbStudentsInFamily = nbStudentsInFamily;
		this.favory = favory;
	}
	
	

}
