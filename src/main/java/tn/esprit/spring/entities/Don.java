package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class Don implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	//Nameof the donater//
	public String SourceDon ;
	public int amount;
	public Date created_at;
	public Date Message;
	
	
	
/*	public Date getMessage() {
		return Message;
	}

	public void setMessage(Date message) {
		Message = message;
	}
*/
	@ManyToOne
	public Jackpot jackpot;
	
	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSourceDon() {
		return SourceDon;
	}

	public void setSourceDon(String sourceDon) {
		SourceDon = sourceDon;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Jackpot getJackpot() {
		return jackpot;
	}

	public void setJackpot(Jackpot jackpot) {
		this.jackpot = jackpot;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
*/
	@ManyToOne
	public User user;

}
