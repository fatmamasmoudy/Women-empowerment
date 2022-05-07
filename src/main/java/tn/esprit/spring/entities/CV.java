package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CV implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCv ;
	private String profileTitle;
	private String aboutMe;
	private String internetSite;
	private String drivingLesence;
	private String linkdIn ;
	private String profileDescription;
	private String centerOfInterest;
	private Byte file ;
	
	 @OneToMany(mappedBy="cv", cascade=CascadeType.ALL)
		
	  	private List<Education> educations;
	 
	/* @OneToMany(mappedBy="cv", cascade=CascadeType.ALL)
		
	  	private List<Interview> interviews;
	  	
	  	
	  	*/
	 
	 @OneToMany(mappedBy="cv", cascade=CascadeType.ALL)
		
	  	private List<Stage> stages;
	 
	 @OneToMany(mappedBy="cv", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
		
	  	private List<Skill> skills;
	 
	 @OneToMany(mappedBy="cv", cascade=CascadeType.ALL)
		
	  	private List<Language> languages;
	 
	 @OneToMany(mappedBy="cv", cascade=CascadeType.ALL)
		
	  	private List<WorkExperience> workExperiences;
	 
	 
	 @OneToOne
	    private User user;
	 
	 
	 public CV(String profileTitle, String aboutMe, String internetSite, String drivingLesence,String linkdIn,String profileDescription ,String centerOfInterest) {
			super();
			this.profileTitle = profileTitle;
			this.aboutMe = aboutMe;
			this.internetSite = internetSite;
			this.drivingLesence = drivingLesence;
			this.linkdIn = linkdIn;
			this.profileDescription = profileDescription;
			this.centerOfInterest = centerOfInterest;
		}



}
