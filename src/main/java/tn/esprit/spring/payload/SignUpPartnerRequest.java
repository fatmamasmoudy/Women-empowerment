package tn.esprit.spring.payload;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.PartnerType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpPartnerRequest {
	private String email;
	private String password;
	private String passwordConfirm;
	private int note;
	private String name;
	private PartnerType type;
	
}
