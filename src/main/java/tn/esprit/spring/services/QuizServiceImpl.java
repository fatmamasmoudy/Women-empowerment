package tn.esprit.spring.services;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Mail;
import tn.esprit.spring.entities.Question;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.QuizResponse;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.QuestionRepository;
import tn.esprit.spring.repository.QuizRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class QuizServiceImpl implements IQuizService {
	@Autowired
	QuizRepository quizRepo;
	@Autowired
	QuestionRepository questionRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	IPdfGenaratorService pdfGenerateService;
	@Autowired
	IMailService mailSrev;
	
	@Value("${pdf.directory}")
    private String pdfDirectory;

	@Override
	@Transactional
	public Quiz addQuiz(Quiz quiz) {
		List<Question> questions = quiz.getQuestions();
		for (Question question : questions) {
			question.setQuiz(quiz);
			questionRepo.save(question);
		}
		return quizRepo.save(quiz);
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) {
		List<Question> questions = quiz.getQuestions();
		for (Question question : questions) {
			question.setQuiz(quiz);
			questionRepo.save(question);
		}
		return quizRepo.save(quiz);
	}

	@Override
	public void deleteQuiz(int idQuiz) {
		Quiz quiz = quizRepo.findById(idQuiz).orElse(null);
		List<Question> questions = quiz.getQuestions();
		questionRepo.deleteAll(questions);
		quizRepo.deleteById(idQuiz);
	}

	@Override
	public List<Quiz> findAllQuizs() {		
		return (List<Quiz>) quizRepo.findAll();
	}

	@Override
	public Quiz findById(int id) {
		return quizRepo.findById(id).orElse(null);
	}

	@Override
	public int doQuiz(long idLearner, List<Question> questions) {
		User learner = userRepo.findById(idLearner);
		
		int note = 0;
		for (Question question : questions) {
			
			if(questionRepo.findById(question.getId()).orElse(null).getReponse().equals(question.getWritedResponse()))
				note+=2;

			question.setQuestion(questionRepo.findById(question.getId()).orElse(null).getQuestion());
			question.setProposition1(questionRepo.findById(question.getId()).orElse(null).getProposition1());
			question.setProposition2(questionRepo.findById(question.getId()).orElse(null).getProposition2());
			question.setProposition3(questionRepo.findById(question.getId()).orElse(null).getProposition3());
			question.setReponse(questionRepo.findById(question.getId()).orElse(null).getReponse());
			
		}
		

		
		Map<String, Object> data = new HashMap<>();
        //Training t = trainingServ.findById(id);
		
        
        data.put("questions", questions);
        data.put("note", note);
        pdfGenerateService.generatePdfFile("Quiz", data, "Quiz"+idLearner+".pdf");
        Mail mail = new Mail();
        //mailSrev.sendMailWithAttachment();
        try {
			mailSrev.sendMailWithAttachment(pdfDirectory +"Quiz"+idLearner+".pdf", learner.getEmail());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}

}
