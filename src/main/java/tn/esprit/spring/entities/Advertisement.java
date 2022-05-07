package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Advertisement  implements Serializable  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String NameAd;
	private String  Canal;
	
	@Temporal(TemporalType.DATE)
	private Date DateDeb;
	
	@Temporal(TemporalType.DATE)
	private Date DateFin;
	
	private int nbVueCible;
	private int NbVueFinal;
	private Double Price;
	private String Upload;
	
	@Enumerated(EnumType.STRING)
	private Domain domain;
	@JsonIgnore
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	
	
	
}
