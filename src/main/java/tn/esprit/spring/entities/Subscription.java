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
public class Subscription implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String designation;
	private int montant;
	private String duration;
	private Date date;
	
	@ManyToOne
	Partner partners;

}
