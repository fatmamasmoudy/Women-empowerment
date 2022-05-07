package tn.esprit.spring.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tn.esprit.spring.entities.Donationuser;
import tn.esprit.spring.entities.Jackpot;
import tn.esprit.spring.entities.User;

public interface IJackpotService {

	
	
	void addJackpot (Jackpot jackpot);
	public List<User> Returnuserbyjackpot( int id);
	public int calculerscore(Donationuser u);
	
	public List<Jackpot> jackpotencrs();
	public  Map<Integer, Integer> afficherlisteneedy();
	public  Map<Integer, Integer> attribuerjackpot(int i);
	
	public Jackpot retrivenextjackpot(int id);
}
