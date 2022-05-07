package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class Activity implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	public String designation;
	public Timestamp hour;
	
	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Timestamp getHour() {
		return hour;
	}

	public void setHour(Timestamp hour) {
		this.hour = hour;
	}

	public Event getEvent() {
		return Event;
	}

	public void setEvent(Event event) {
		Event = event;
	}*/
@JsonIgnore
	@ManyToOne
	public Event Event;
}
