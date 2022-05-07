package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.Reaction;
import tn.esprit.spring.services.IReactionService;

@RestController
@RequestMapping("/reaction")
public class ReactionRestController {
	@Autowired
	IReactionService reactServ;
	
	@PostMapping("/add")
	@ResponseBody
	public Reaction addReaction(@RequestBody Reaction r)
	{
		return reactServ.addReaction(r);
	}
	
	@PutMapping("/update")
	@ResponseBody
	public Reaction updateReaction(@RequestBody Reaction r) {
		return reactServ.updateReaction(r);
	}
	
	@DeleteMapping("/remove/{id}")
	@ResponseBody
	public void removeReaction(@PathVariable("id") int id) {
		reactServ.deleteReaction(id);
	}

}
