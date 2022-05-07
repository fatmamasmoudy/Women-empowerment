package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor

public class Condidacy implements Serializable {

	@Override
	public String toString() {
		return "Condidacy [id=" + id + ", created_at=" + created_at + ", state=" + state + ", type=" + type + ", score="
				+ score + ", candidat=" + candidat + ", module=" + module + ", jobOffer=" + jobOffer + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date created_at;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	 
	@Transient
	private int score;
	
	
    @ManyToOne
    private User candidat;
    
    @ManyToOne
	private Module module;
    
    @ManyToOne
    private JobOffer jobOffer;
  
    @Temporal(TemporalType.DATE)
	private Date interview_date;
	

	
	
	
    

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}