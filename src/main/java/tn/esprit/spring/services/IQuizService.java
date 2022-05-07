package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Question;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizResponse;

public interface IQuizService {
	
	Quiz addQuiz(Quiz quiz);
	
	Quiz updateQuiz(Quiz quiz);
	
	void deleteQuiz(int idQuiz);
	
	List<Quiz> findAllQuizs();
	
	Quiz findById(int id);
	
	int doQuiz(long idLearner, List<Question> questions);

}
