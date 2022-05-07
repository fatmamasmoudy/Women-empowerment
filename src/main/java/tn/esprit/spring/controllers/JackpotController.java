package tn.esprit.spring.controllers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Donationuser;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Jackpot;
import tn.esprit.spring.repository.DonationuserRepository;
import tn.esprit.spring.services.IJackportServiceImpl;
import tn.esprit.spring.services.IJackpotService;

@RestController
@RequestMapping("/jackpot")
public class JackpotController {

	@Autowired
	IJackpotService js;
	@Autowired 
	DonationuserRepository dr;
	
	
	@PostMapping("/ajout-jackpot")
	public void addjackpot(@RequestBody Jackpot j)
	{
		js.addJackpot(j);
	}
	
	@GetMapping("/jackpots-encours")
	public List<Jackpot> getalljackpotsencours()
	{
		return js.jackpotencrs();
	}
	@GetMapping("/listegangant")
	public  Map<Integer, Integer> listegagants()
	{
		return js.afficherlisteneedy();
	}
	@GetMapping("/score/{id}")
	public int calculer (@PathVariable("id")int id)
	{	Donationuser u=dr.findById(id).orElse(null);
		return js.calculerscore(u);
	}
	@GetMapping("/att/{id}")
	public Map<Integer, Integer> attribuerjackpot(@PathVariable("id") int i)
	{
		return js.attribuerjackpot(i);
	}
}
