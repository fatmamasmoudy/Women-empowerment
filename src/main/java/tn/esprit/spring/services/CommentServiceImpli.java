package tn.esprit.spring.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.repository.CommentRepository;
@Service
public class CommentServiceImpli implements ICommentService {
@Autowired 
CommentRepository commentRepository; 


	@Override
	public List<Comment> retrieveAllComment() {
		System.out.println(commentRepository.commentsPlusPer());
 		return commentRepository.commentsPlusPer();
	}

	@Override
	public Comment addComment(Comment S) {
		String comment = S.getContent();
		S.setContent(BadWordFilter.getCensoredText(comment));
		Date currentUtilDate = new Date();
		S.setCreatedAt(currentUtilDate);
		S.setUpdatedAt(null);
		S.setNbLike(0);
		S.setNbDislike(0);
		 return commentRepository.save(S);
	}

	@Override
	public void deleteComment(int id) {
		commentRepository.deleteById(id);		
	}

	@Override
	public Comment updateComment(Comment S) {
		Date currentUtilDate = new Date();
		S.setCreatedAt(null);
		S.setUpdatedAt(currentUtilDate);
		return 	commentRepository.save(S);
	}

	@Override
	public Comment retrieveComment(int id) {
			
			return commentRepository.findById(id).orElse(null);
	}
	
	
}

