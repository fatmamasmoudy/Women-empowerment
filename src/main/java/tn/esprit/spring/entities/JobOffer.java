package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class JobOffer implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	 private int idJOffer;
	 private String poste ;
	 private String description ; 
	 private String place ; 
	 private String salary ; 
	 
	 @Temporal(TemporalType.DATE)	
	 private Date created_at ;
	 
	 @Temporal(TemporalType.DATE)	
	 private Date validate_at;
	 
	 @Temporal(TemporalType.DATE)	
	 private Date update_at;
	 
	 private String file ;	 	 
	 
	    @ManyToOne
	    private Partner candidature;
	   	    
	    @OneToMany(mappedBy="jobOffer", cascade=CascadeType.ALL)		
		private List<Condidacy> condidacies;

	    
}
