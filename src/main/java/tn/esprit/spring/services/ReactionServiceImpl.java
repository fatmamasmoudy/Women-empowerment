package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Reaction;
import tn.esprit.spring.repository.ReactionRepository;

@Service
public class ReactionServiceImpl implements IReactionService {
	@Autowired
	ReactionRepository reactionRepo;

	@Override
	public Reaction addReaction(Reaction reaction) {
		return reactionRepo.save(reaction);
	}

	@Override
	public Reaction updateReaction(Reaction reaction) {
		return reactionRepo.save(reaction);
	}

	@Override
	public void deleteReaction(int idReaction) {
		reactionRepo.deleteById(idReaction);

	}

	@Override
	public List<Reaction> findAllReactions() {
		return (List<Reaction>) reactionRepo.findAll();
	}


}
