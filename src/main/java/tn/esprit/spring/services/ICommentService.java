package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Comment;


public interface ICommentService {
	List<Comment> retrieveAllComment();
	Comment addComment(Comment S);
	void deleteComment(int id);
	Comment updateComment(Comment c);
	Comment retrieveComment(int id);
}
