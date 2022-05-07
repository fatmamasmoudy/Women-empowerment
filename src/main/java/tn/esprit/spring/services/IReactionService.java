package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Reaction;

public interface IReactionService {
	Reaction addReaction(Reaction reaction);
	
	Reaction updateReaction(Reaction reaction);
	
	void deleteReaction(int idReaction);
	
	List<Reaction> findAllReactions();

}
