package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Activity;
import tn.esprit.spring.entities.Etat;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Jackpot;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.JackpotRepository;
import tn.esprit.spring.repository.UserRepository;



@Service
public class IEventServiceImpl implements IEventService{

	@Autowired EmailSenderService emails;
	
	@Autowired 
	UserRepository ur;
	@Autowired
	EventRepository er;
	
	@Autowired 
	IJackpotService js;
	@Autowired
	JackpotRepository jr;
	
	 @Value("${image.directory}")
	    private static String imgDirectory;
	
	/*@Override
	public void addEvent(Event event,Jackpot j) {
		er.save(event);
		
		Jackpot j = new Jackpot();
		j.setDesignation(designation);
		j.setGoal(goal);
		j.setEvent(event);
		j.setEvent(event);
		
		js.addJackpot(j);
		
	}*/
	@Override
	public void suppEvent(int id) {
		er.deleteById(id);
		
	}
	@Override
	public List<Event> getAllEvents() {
		List<Event> x = new ArrayList<>();
		for (Event e:er.findAll()){
			x.add(e);
		}
		return x;
	}
	@Override
	public Event getEventById(int id) {
	
		return er.findById(id).orElse(null);
	}
	
	@Override
	public List<Activity> GetactByidevent(int id) {
			Event event=er.findById(id).orElse(null);
			
		return event.getActivitys();
	}
	@Override
	public void addEvent(Event e) {
		er.save(e);
		
	}
	@Override
	public void addEvent(Event event, String designation, String goal,int amount) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		er.save(event);
		Jackpot j=new Jackpot();
		j.setEvent(event);
		j.setDesignation(designation);
		j.setGoal(goal);
		j.setAmount(amount);
		j.setCreated_at(date);
		j.setEtat(Etat.PASENCORE);
		jr.save(j);
		
	}
	@Override
	public List<Event> geteventsbydescription(String description) {
		return er.retrieveEventsearch(description);
		
	}
	//@EventListener(ApplicationReadyEvent.class)
	public void triggerMain() throws MessagingException {

//		emails.sendMailWithAttachment("khaled.abdelmoumen98@gmail.com",
//				"hello, our event started we re welcoming you",
//				"Invitation","D:/pidev spring/PIDEV-Ahmed/uploads/18/diagramme de classe finale.PNG");

	}
	@Scheduled(cron = "0 * * * * *" )
	@Override
	public void inviteparticipant() {
		
		for (Event e:er.findAll())
		{
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			if (e.getEventDate() !=null)
			{
			if ( e.getEventDate().compareTo(date) <= 0 )
			{
				
				if ( e.getParticipants() !=null )
				{
				for (User u:e.getParticipants())
				{
					try {
						String name =u.getFirstName() + " "+ u.getLastName(); 
						//triggerMain();
						emails.sendMailWithAttachment(u.getEmail(),
								"hello " + name+ ", our event started we re welcoming you",
								"Invitation","D:\\pidev spring\\PIDEV-Ahmed2\\uploads\\"+e.getPhotos());
					} catch (MessagingException e1) {
						
						e1.printStackTrace();
					}
				}
				}
			}
			}
		}
		
	}
	@Override
	public void participate(int id_evenement, long id_user) {
		Event e=er.findById(id_evenement).orElse(null);
			User u=ur.findById(id_user);
			/*List <User> list=e.getParticipants();
			list.add(u);
			e.setParticipants(list);
			er.save(e);*/
			List<Event> le=u.getEvents();
			le.add(e);
			u.setEvents(le);
			ur.save(u);
		
	}

}
