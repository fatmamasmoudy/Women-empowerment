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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Module.Name;
import tn.esprit.spring.entities.Module.Traitement;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module implements Serializable {

	@Override
	public String toString() {
		return "Module [id=" + id + ", capacity=" + capacity + ", nbreInscription=" + nbreInscription + ", name=" + name
				+ ", traitement=" + traitement + ", partner=" + partner + "]";
	}


	public enum Traitement{
		Automatique,Manuelle
	}
	
	public enum Name{
		Informatique,Economique,Medecine,Mecanique
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int capacity;
	private int nbreInscription;
	@Enumerated(EnumType.STRING)
	private Name name;
	@Enumerated(EnumType.STRING)
	private Traitement traitement;
	
	
	@JsonIgnore
	@ManyToOne
	private Partner partner;

}
