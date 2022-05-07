package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSkill;
	private String designation ;
	private String level ;
	
	@ManyToOne
	private CV cv;

	
	@Override
	public String toString() {
		return "Skill [idSkill=" + idSkill + ", designation=" + designation + ", level=" + level + ", cv=" + cv + "]";
	}
}
