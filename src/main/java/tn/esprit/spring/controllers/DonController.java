package tn.esprit.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Don;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Jackpot;
import tn.esprit.spring.services.IDonService;
import tn.esprit.spring.services.IJackpotService;

@RestController
@RequestMapping("/don")
public class DonController {

	
	@Autowired
	IDonService ds;
	
	
	@PostMapping("/ajout-don")
	public void adddon(@RequestBody Don d)
	{
		ds.addDon(d);
	}
	
	
	@GetMapping("/alldons/{id}")
	public List<Don> getDonByid(@PathVariable("id") int id)
	{
		
				
		return ds.getDonByJackpot(id);
	}
}
