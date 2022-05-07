package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Parameters implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	private int monthlyincome_420;
	private int monthlyincome_800;
	private int nbPersFamily_4;
	private int nbPersFamily_5;
	private int nbStudentsInFamily_1;
	private int nbStudentsInFamily_2;
	private int nbStudentsInFamily_3;
	private int medicalNeed;
	private int socialNeed;
	private int needy;
	private int unemployed;
	private int alreadywon;
}
