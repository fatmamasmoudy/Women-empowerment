package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Caisse implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	public int amout;
	
	
	@OneToMany(cascade= CascadeType.ALL,mappedBy="caisse")
	public List<Jackpot> jackpots;
	
}
