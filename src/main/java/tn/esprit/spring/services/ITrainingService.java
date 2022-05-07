package tn.esprit.spring.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Domain;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.Training;

public interface ITrainingService {
	
	Training addTraining(Training training);
	
	Training updateTraining(Training training);
	
	void deleteTraining(int idTraining);
	
	void assignQuizToTraining(int idTraining, int idQuiz);
	
	Training findById(int id);
	
	List<Training> getAllTrainings();
		
	List<Training> findBySubjectContains(String subject);
	
	List<Training> findByTrainerName(String trainer);
	
	List<Training> findByEndDateIsBefore(Date d);
	
	void participe(long idLearner, int idTraining);
	
	void evaluateTraining(int idTraining, Evaluation e);
	
	int nbreTrainingInProgressByTrainerName(String trainer);
	
	List<Training> searchTraining(long userId, String str)throws Exception;
	
	int duree();
	
	List<Training> searchTrainingByTiming(Double timing);
	
	String  domain(String str);
	
	public List<Training> suggestion(long userId);
	
	List<Training> EndTraining();

}
