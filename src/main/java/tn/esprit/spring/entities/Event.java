package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	public String description;
	public Date eventDate;
	public Date endDate;
	public Date created_at;
	public String domain;
	 @Column(nullable = true, length = 64)
	  
	public String photos;
	
	 @Transient
	    public String getPhotosImagePath() {
	        if (photos == null ) return null;
	         
	        return "/user-photos/"  + photos;
	    }
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="Event")
	public List<Activity> activitys;
	
	
	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<Activity> getActivitys() {
		return activitys;
	}

	public void setActivitys(List<Activity> activitys) {
		this.activitys = activitys;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public Jackpot getJackpot() {
		return jackpot;
	}

	public void setJackpot(Jackpot jackpot) {
		this.jackpot = jackpot;
	}*/
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="events",fetch = FetchType.EAGER)
	public List<User> participants;
	@JsonIgnore
	@OneToOne(mappedBy="event")
	public Jackpot jackpot;
	
	
}


