package tn.esprit.spring.services;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import tn.esprit.spring.controllers.TrainingRestController;
import tn.esprit.spring.entities.Domain;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.IntrestedBy;
import tn.esprit.spring.entities.Mail;
import tn.esprit.spring.entities.Quiz;
import tn.esprit.spring.entities.Training;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.PenalityRepository;
import tn.esprit.spring.repository.QuizRepository;
import tn.esprit.spring.repository.TrainingRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class TrainingServiceImpl implements ITrainingService {
	@Autowired
	TrainingRepository trainingRepo; 
	@Autowired
	QuizRepository quizRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	IPdfGenaratorService pdfGenerateService;
	
	@Autowired
    private QRCodeServiceImpl qrCodeService;
	
	@Autowired
	IMailService mailSrev;
	
	@Autowired
	IPenalityService pServ;
	
	private Logger logger = LoggerFactory.getLogger(TrainingRestController.class);
	
	 @Value("${qrcode.directory}")
	    private String qrCodeDirectory;
	 
	 @Value("${file.upload-dir}")
		String FILE_DIRECTORY;
	 
	 @Value("${dictionnaire.directory}")
	    private String dictionnaireDirectory;
	
	String cuisine,
	 coiffure,
	 couture,
	 informatique,
	 sante,
	 architecture,
	 entreprenariat,
	 architacture,
	 marketing,
	 economie,
	 communication,
	 mecanique;

	@Override
	public Training addTraining(Training training) {
		if(trainingRepo.nbreTrainingInProgressByTrainerName(training.getTrainerName())<2)
			return trainingRepo.save(training);
		else return null;
	}

	@Override
	public Training updateTraining(Training training) {
		return trainingRepo.save(training);
	}
	
	@Override
	public void evaluateTraining(int idTraining, Evaluation e) {
		int note = e.getNote1()+e.getNote2()+e.getNote3()+e.getNote4()+e.getNote5();
		Training t = trainingRepo.findById(idTraining).orElse(null);
		int nb = t.getNbEvaluation()+1;
		t.setNbEvaluation(nb);
		t.setNote((int) Math.round((t.getNote()+note)/nb));
		trainingRepo.save(t);				
	}

	@Override
	public void deleteTraining(int idTraining) {
		trainingRepo.deleteById(idTraining);		
	}

	@Override
	public List<Training> getAllTrainings() {
		return (List<Training>) trainingRepo.findAll();
	}

	@Override
	public List<Training> findBySubjectContains(String subject) {
		System.out.println(trainingRepo.findBySubjectContains(subject).size());
		return trainingRepo.findBySubjectContains(subject);
	}

	@Override
	public Training findById(int id) {
		return trainingRepo.findById(id).orElse(null);
	}

	@Override
	public void assignQuizToTraining(int idTraining, int idQuiz) {
		Training t = trainingRepo.findById(idTraining).orElse(null);
		Quiz q = quizRepo.findById(idQuiz).orElse(null);
		
		t.setQuiz(q);
		trainingRepo.save(t);		
	}

	@Override
	public List<Training> findByTrainerName(String trainer) {
		return trainingRepo.findByTrainerName(trainer);
	}

	@Override
	public List<Training> findByEndDateIsBefore(Date d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void participe(long idLearner,int idTraining) {
		User user = userRepo.findById(idLearner);
		boolean deja = false;
		Training t = trainingRepo.findById(idTraining).orElse(null);
		
		t.getLearners().add(user);
		for (Training train : user.getTrainings()) {
			if(train.getSubject().contains(t.getSubject()))
			{
				deja = true;
				
			}
			System.out.println(train.getSubject());
		}
		if(deja ==false)
		{

			trainingRepo.save(t);
		}
	}

	@Override
	public int nbreTrainingInProgressByTrainerName(String trainer) {  
		System.out.println(trainingRepo.nbreTrainingInProgressByTrainerName(trainer));
		return trainingRepo.nbreTrainingInProgressByTrainerName(trainer);
	}

	@Override
	public List<Training> searchTraining(long userId, String str) throws Exception{	
		User user = userRepo.findById(userId);
		IntrestedBy intrestedBy = user.getIntrestedBy();
		
		Domain domain = null;
		List<Training> result = new ArrayList<Training>();
		
		if (new File(dictionnaireDirectory+"cuisine.txt").exists())
		{	
			cuisine = loadFile(new File(dictionnaireDirectory+"cuisine.txt"));
			if(cuisine !=null && cuisine.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setCuisineValue(intrestedBy.getCuisineValue()+1);
				domain = Domain.Cuisine;
				System.out.println("cuisine okk");
			}
		}
		if (new File(dictionnaireDirectory+"coiffure.txt").exists())
		{
			coiffure = loadFile(new File(dictionnaireDirectory+"coiffure.txt"));
			if(coiffure !=null && coiffure.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setCoiffureValue(intrestedBy.getCoiffureValue()+1);
				domain = Domain.Coiffure;
				System.out.println("coiffure okk");
			}
		}
		if (new File(dictionnaireDirectory+"couture.txt").exists())
		{
			couture = loadFile(new File(dictionnaireDirectory+"couture.txt"));
			if(couture !=null && couture.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setCoutureValue(intrestedBy.getCoutureValue()+1);
				domain = Domain.Couture;
				System.out.println("couture okk");
			}
		}
		if (new File(dictionnaireDirectory+"informatique.txt").exists())
		{
			informatique = loadFile(new File(dictionnaireDirectory+"informatique.txt"));
			if(informatique !=null && informatique.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setInformatiqueValue(intrestedBy.getInformatiqueValue()+1);
				domain = Domain.Informatique;
				System.out.println("informatique okk");
			}
		}
		
		if (new File(dictionnaireDirectory+"entreprenariat.txt").exists())
		{
			entreprenariat = loadFile(new File(dictionnaireDirectory+"entreprenariat.txt"));
			if(entreprenariat !=null && entreprenariat.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setEntreprenariatValue(intrestedBy.getEntreprenariatValue()+1);
				domain = Domain.Entreprenariat;
				System.out.println("entreprenariat okk");
			}
		}
		
		if (new File(dictionnaireDirectory+"architecture.txt").exists())
		{
			architecture = loadFile(new File(dictionnaireDirectory+"architecture.txt"));
			if(architecture !=null && architecture.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setArchitectureValue(intrestedBy.getArchitectureValue()+1);
				domain = Domain.Architacture;
				System.out.println("architecture okk");
			}
		}
		
		if (new File(dictionnaireDirectory+"marketing.txt").exists())
		{
			marketing = loadFile(new File(dictionnaireDirectory+"marketing.txt"));
			if(marketing !=null && marketing.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setMarketingValue(intrestedBy.getMarketingValue()+1);
				domain = Domain.Marketing;
				System.out.println("marketing okk");
			}
		}
		
		if (new File(dictionnaireDirectory+"sante.txt").exists())
		{
			sante = loadFile(new File(dictionnaireDirectory+"sante.txt"));
			if(sante !=null && sante.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setSanteValue(intrestedBy.getSanteValue()+1);
				domain = Domain.Sante;
				System.out.println("sante okk");
			}
		}		
		if (new File(dictionnaireDirectory+"economie.txt").exists())
		{
			economie = loadFile(new File(dictionnaireDirectory+"economie.txt"));
			if(economie !=null && economie.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setEconomieValue(intrestedBy.getEconomieValue()+1);
				domain = Domain.Economie;
				System.out.println("economie okk");
			}
		}		
		if (new File(dictionnaireDirectory+"communication.txt").exists())
		{
			communication = loadFile(new File(dictionnaireDirectory+"communication.txt"));
			if(communication !=null && communication.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setCommunicationValue(intrestedBy.getCommunicationValue()+1);
				domain = Domain.Communication;
				System.out.println("communication okk");
			}
		}		
		if (new File(dictionnaireDirectory+"mecanique.txt").exists())
		{
			mecanique = loadFile(new File(dictionnaireDirectory+"mecanique.txt"));
			if(mecanique !=null && mecanique.toLowerCase().contains(str.toLowerCase()))
			{	
				intrestedBy.setMecaniqueValue(intrestedBy.getMecaniqueValue()+1);
				domain = Domain.Mecanique;
				System.out.println("mecanique okk");
			}
		}		
		result.addAll(trainingRepo.findByDomain(domain));
		result.addAll(trainingRepo.searchTraining(str));
	
		userRepo.save(user);
		return result;
	}
	
//	if(trainingRepo.domain(str)!=null)
//	{
//		if(trainingRepo.domain(str).equals("Couture"))
//			intrestedBy.setCoutureValue(intrestedBy.getCoutureValue()+1);
//		if(trainingRepo.domain(str).equals("Coiffure"))
//			intrestedBy.setCoiffureValue(intrestedBy.getCoiffureValue()+1);
//		if(trainingRepo.domain(str).equals("Cuisine"))
//			intrestedBy.setCuisineValue(intrestedBy.getCuisineValue()+1);
//	}

	@Override
	public int duree() {
		return trainingRepo.duree();
	}

	@Override
	public List<Training> searchTrainingByTiming(Double timing) {
		return trainingRepo.searchTrainingByTiming(timing*30);
	}

	@Override
	public String domain(String str) {
		return trainingRepo.domain(str);
	}

	@Override
	public List<Training> suggestion(long userId) {
		Domain d = null;
		User user = userRepo.findById(userId);
		if( user.getIntrestedBy().getCoiffureValue() >= user.getIntrestedBy().getCoutureValue() && user.getIntrestedBy().getCoiffureValue() >= user.getIntrestedBy().getCuisineValue())
			d=Domain.Coiffure;
	      else if (user.getIntrestedBy().getCoutureValue() >= user.getIntrestedBy().getCoiffureValue() && user.getIntrestedBy().getCoutureValue() >= user.getIntrestedBy().getCuisineValue())
	          d=Domain.Couture;
	      else
	    	  d=Domain.Cuisine;
		return trainingRepo.findByDomain(d);
	}

	@Override
	public List<Training> EndTraining() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public String loadFile(File file)throws Exception{
		String s = "";
		String st;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((st = br.readLine()) != null)

        s=s+st;
		System.out.println("*******s");
		System.out.println(s);
		return s;
		
	}
	
//	@Scheduled(cron="0 0 22 * * *")
//	@Scheduled(cron="*/30 * * * * *")
	private void GenerateCertif() {
		Map<String, Object> data = new HashMap<>();
        
        
        
		System.out.println("hello");
		for (Training t : trainingRepo.EndTraining()) {
			data.put("t", t);
			for (User user : t.getLearners()) {
				if(pServ.exclure(user.getId(), t.getId()).size()==0)
				{
					data.put("user", user);
		        	pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+t.getId()+user.getId()+".pdf");
		        	String filePath = qrCodeDirectory+t.getId() + user.getId() + ".png";
		            String qrCodeContent = "Simple Solution " + user.getId();
		            int width = 400;
		            int height = 400;
		            //boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
		            boolean result = qrCodeService.generateQRCode("https://drive.google.com/file/d/1vH-nAMnlvgKyIOaWPuBwOWDiDZSBEI5j/view?usp=sharing", filePath, width, height);
		            if(result) 
		            {
		                logger.info("Generate QR code file " + filePath + " successfully");
		                data.put("user", user);        	
		            }
		            
		            try {
		    			mailSrev.envoyer("Congratulations, Your Certificate is Ready!","Congratulations! You’ve successfully completed "+ t.getSubject()+". Scan your QRCODE or visit this link "+"https://drive.google.com/file/d/1vH-nAMnlvgKyIOaWPuBwOWDiDZSBEI5j/view?usp=sharing"+" to download your certificate.", filePath, user.getEmail());
		    		} catch (MessagingException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		        	
		            data.put("result", result);
				}
	        	
	        	//pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+user.getId()+".pdf");
	            //pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+t.getId()+user.getId()+".pdf");
			}			
		}
	}
	
	
	
	

}
