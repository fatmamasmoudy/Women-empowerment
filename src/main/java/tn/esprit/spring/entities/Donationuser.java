package tn.esprit.spring.entities;

import java.util.Date;
import java.util.List;

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
public class Donationuser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	private String firstName;
	private String lastName;
	private float monthlyincome;
	private int nbPersFamily;
	private int nbStudentsInFamily;
	private String medicalNeed;
	private String socialNeed;
	private String needy;
	private String unemployed ;
	private double amoutwon;
	
	
}
