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
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.services.CommentServiceImpli;
 @RestController
@RequestMapping("/Comment")
public class CommentRestController {
	@Autowired
	CommentServiceImpli commentServiceImpli ; 
		
		// http://localhost:8083/women/Comment/All 
		@GetMapping("/All")
		public List<Comment> getComments() {
		List<Comment> listComment = commentServiceImpli.retrieveAllComment();
		return listComment;
		
		}
		// http://localhost:8083/women/Comment/ShowComment/{Comment-id}
		@GetMapping("/ShowComment/{Comment-id}")
		public Comment retrieveComment(@PathVariable("Comment-id") int id) {
		return commentServiceImpli.retrieveComment(id);  
		}
		// http://localhost:8083/women/Comment/AddComment
		
		@PostMapping("/AddComment")
		public Comment addSubject(@RequestBody  Comment c) {
		return commentServiceImpli.addComment(c);
		}
		// http://localhost:8083/women/Comment/DeleteComment/{Comment-id}
		
		@DeleteMapping("/DeleteComment/{Comment-id}")
		public void DeleteComment(@PathVariable("Comment-id") int id) {
			commentServiceImpli.deleteComment(id);  
		}
		// http://localhost:8083/women/Comment/UpdateComment
		
		@PutMapping("/UpdateComment")
		public Comment updateSubject(@RequestBody Comment comment) {
		return commentServiceImpli.updateComment(comment);
		}
		
}