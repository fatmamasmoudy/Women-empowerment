package tn.esprit.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Penality;
import tn.esprit.spring.entities.Training;
import tn.esprit.spring.services.IPenalityService;

@RestController
@RequestMapping("/penality")
public class PenalityRestController {
	@Autowired
	IPenalityService penalityServ;
	
	@PostMapping("/add/{idLearner}/{idTraining}")
	@ResponseBody
	public Penality addPenality(@RequestBody Penality p, @PathVariable("idLearner") int idLearner, @PathVariable("idTraining") int idTraining)
	{
		return penalityServ.addPenalityToLearner(p, idLearner, idTraining);
	}
	
	@PutMapping("/update")
	@ResponseBody
	public Penality updatePenality(@RequestBody Penality p) {
		return penalityServ.updatePenality(p);
	}
	
	@DeleteMapping("/remove/{id}")
	@ResponseBody
	public void removePenality(@PathVariable("id") int id) {
		penalityServ.deletePenality(id);
	}
	
	@GetMapping("/retrieve-by-learner/{idLearner}")
	@ResponseBody
	public List<Penality> retrieveByLearner(@PathVariable("idLearner") int id){
		return penalityServ.findByLearner(id);
	}
	
	@GetMapping("/retrieve-all-penalities")
	@ResponseBody
	public List<Penality> getPenalities() {
		return penalityServ.findAllPenalities();
	}
	
	@GetMapping("/exclure/{learner}/{training}")
	@ResponseBody
	public List<Penality> exclure(@PathVariable("learner") int learner,@PathVariable("training") int training) {
		return penalityServ.exclure(learner,training);
	}

}
