package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Training implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String subject;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	private int nbreMax;
	private int nbreParticipation;
	private int note;
	private int nbEvaluation;
	
	@Enumerated(EnumType.STRING)
	private Domain domain;
	
	private String cours;
	private boolean isCertified;
	private String trainerName;
	@JsonIgnore
	private String seances;
	
	@JsonIgnore
	@OneToMany(mappedBy = "training", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Certificate> certificates;
	
	@ManyToOne
	private Quiz quiz;
	@JsonIgnore
	@OneToMany(mappedBy = "training", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Penality> penalities;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<User> learners;
	
	

}
