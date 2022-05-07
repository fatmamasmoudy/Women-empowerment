package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
public class Jackpot implements Serializable{

	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	public Date created_at;
	public Date updated_at;
	public int amount;
	public String designation;
	public String goal;
	public Etat etat;
	@OneToOne
	public Event event;
	
	@JsonIgnore
	@OneToMany(cascade= CascadeType.ALL,mappedBy="jackpot",  orphanRemoval = true)
	public List<Don> dons;
	
	
	@ManyToOne
	public Caisse caisse;
}
