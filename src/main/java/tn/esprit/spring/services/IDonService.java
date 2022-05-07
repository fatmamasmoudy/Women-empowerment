package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Don;

public interface IDonService {

	
	
	public void addDon(Don don);
	public List<Don> getDonByJackpot(int id);
}
