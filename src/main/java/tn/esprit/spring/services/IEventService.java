package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;

import tn.esprit.spring.entities.Activity;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Jackpot;

public interface IEventService {

	
	void addEvent(Event e);
	
	void addEvent(Event event,String designation , String goal,int amount);
	void suppEvent(int id);
	public List<Event> getAllEvents();
	public Event getEventById(int id);
	public List<Activity> GetactByidevent(int id);
	 public List<Event> geteventsbydescription(String description);
	 
	 public void inviteparticipant();
	 public void participate(int id_evenement,long id_user);
}
