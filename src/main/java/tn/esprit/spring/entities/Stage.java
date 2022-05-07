package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Stage implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStage ;
	private String establishment ;
	private String location ; 
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="MM/dd/yyyy")
	private Date startDate ;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="MM/dd/yyyy")
	private Date endDate ;
	
	private String description ;
	
	@ManyToOne
	private CV cv;
	
	
}
