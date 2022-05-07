package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IntrestedBy implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int CoiffureValue;
	private int CoutureValue;
	private int CuisineValue;
	private int informatiqueValue;
	private int entreprenariatValue;
	private int architectureValue;
	private int marketingValue;
	private int santeValue;
	private int economieValue;
	private int communicationValue;
	private int mecaniqueValue;
	
	
	@OneToOne(mappedBy="intrestedBy")
	private User user;

}
