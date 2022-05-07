package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Don;
import tn.esprit.spring.entities.Donationuser;
import tn.esprit.spring.entities.Etat;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Jackpot;
import tn.esprit.spring.entities.Parameters;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.DonationuserRepository;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.JackpotRepository;
import tn.esprit.spring.repository.ParametersRepository;

@Service
public class IJackportServiceImpl implements IJackpotService{

	@Autowired
	DonationuserRepository dr;
	
	@Autowired
	ParametersRepository pr;
	
	@Autowired
	JackpotRepository jr;
	@Autowired 
	EventRepository er;
	
	@Override
	public void addJackpot(Jackpot jackpot) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		jackpot.setEtat(Etat.ENCOURS);
		jackpot.setCreated_at(date);
		jr.save(jackpot);
		
	}
	
	@Override
	public List<User> Returnuserbyjackpot(int id) {
		Jackpot j = jr.findById(id).orElse(null);
		 List<User> user = new ArrayList<User>();
		
		for ( Don d:j.getDons() )
		{
			user.add(d.getUser());
		}
		return user;
	}
	
	@Override
	public int calculerscore (Donationuser u) {
		int score = 0 ;
		Parameters p= pr.findById(1).orElse(null);
		
		if (u.getUnemployed().equals("yes"))
			
			
		{score = score + p.getUnemployed();
		}
		
		if ( u.getSocialNeed().equals("yes"))
		{
			score = score + p.getSocialNeed();
			
		}
		if (u.getMedicalNeed().equals("yes"))
		{
			score = score + p.getMedicalNeed();
		}
		if (u.getNeedy().equals("yes"))
		{
			score = score + p.getNeedy();
		}
		if ( u .getNbPersFamily() == 4 )
		{score = score + p.getNbPersFamily_4();
		}
		if ( u .getNbPersFamily() >= 5 )
		{score = score + p.getNbPersFamily_5();
		}
		if ( u.getMonthlyincome() <= 420 )
		{
			score = score + p.getMonthlyincome_420();
		}
		if ((  u.getMonthlyincome() <= 800) && (u.getMonthlyincome() >= 420))
		{
			score = score + p.getMonthlyincome_800();
		}
		if ( u.getNbStudentsInFamily() == 1)
		{
			score =score+ p.getNbStudentsInFamily_1();
		}
		if ( u.getNbStudentsInFamily() == 2)
		{
			score =score+ p.getNbStudentsInFamily_2();
		}
		if ( u.getNbStudentsInFamily() == 3)
		{
			score =score+ p.getNbStudentsInFamily_3();
		}
		if ( u.getAmoutwon() != 0)
		{
			score = score - p.getAlreadywon();
		}
		
		return score;
	}
	
	

	@Override
	public List<Jackpot> jackpotencrs() {
		List <Jackpot> j = new ArrayList<>();
		for (Jackpot jp:jr.findAll())
		{
			if (jp.getEtat()== Etat.ENCOURS)
			{
				j.add(jp);
			}
		}
		return j;
	}

	@Override
	public Map<Integer, Integer> afficherlisteneedy() {
		 Map<Integer, Integer> Listdonation = new HashMap<Integer, Integer>();
		 
		 for (Donationuser u:dr.findAll())
		 {		
			 Listdonation.put(u.getId(),calculerscore(u));
		 }
		
			
			Map<Integer, Integer> result = Listdonation.entrySet().stream()
	                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
	                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			
		return result;
	}

	@Override
	public Map<Integer, Integer> attribuerjackpot(int i) {
		 Map<Integer, Integer> Listdonation = afficherlisteneedy();
		 Map<Integer, Integer> Listeargent =  new HashMap<Integer, Integer>();
		 Jackpot j=jr.findById(i).orElse(null);
		 int amount = j.getAmount();
		 
		 for (Integer key : Listdonation.keySet()) {
	          //mapentry.getKey() 
	         // mapentry.getValue());
			Donationuser d = dr.findById(key).orElse(null);
			int help = 0 ;
			if (d.getMedicalNeed()=="yes")
			{
				help = help +1000;
			}
			if (d.getSocialNeed()=="yes")
			{
				help = help + 500;
			}
			if (d.getNbPersFamily() <= 4)
			{
				help = help + 1200;
			}

			double a = amount * 0.10 +help ;
			if (amount > 0)
			{ 
				d.setAmoutwon(a);
				
				amount = (int) (amount - a) ;
			}
			 dr.save(d);
			
			 Listeargent.put(key, (int) a);
	        }
		 j.setEtat(Etat.FINIS);
		 jr.save(j);
		 
		 if ((amount > 0 ))
		 {
			 Jackpot next_jackpot=retrivenextjackpot(j.getId());
			 next_jackpot.setAmount(next_jackpot.getAmount()+amount);
			 jr.save(next_jackpot);
		 }
		return Listeargent;
	}
	
	
	@Scheduled(cron = "0 * * * * *" )
	public void majdesjackpot()
	{
		Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
		/* 
		for(Event e: er.findAll())
		{
			if ( e.getEndDate().compareTo(date) >= 0 )
				
			{
			  if (e.getJackpot() != null )
			{
				Jackpot j = e.getJackpot();
				j.setEtat(Etat.FINIS);
				jr.save(j);
				e.setJackpot(j);
				er.save(e);
			}
		}
		
	

	
	}*/
	for (Jackpot j:jr.findAll())
	{
		
		if (j.getEvent() != null)
		{
		Event e = j.getEvent();
		if ( e.getEndDate().compareTo(date) <= 0 )
		{
			j.setEtat(Etat.FINIS);
			jr.save(j);
			e.setJackpot(j);
			er.save(e);
		}
		}
	}

	}

	@Override
	public Jackpot retrivenextjackpot(int id) {
		Jackpot j1=jr.findById(id).orElse(null);
		Jackpot j2=jr.retrivenextjackpot(id);
		return j2;
	}
}
	
	

