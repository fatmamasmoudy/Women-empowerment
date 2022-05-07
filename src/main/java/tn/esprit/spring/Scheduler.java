package tn.esprit.spring;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Training;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.TrainingRepository;
import tn.esprit.spring.services.IPdfGenaratorService;
import tn.esprit.spring.services.QRCodeServiceImpl;

@Component
@Slf4j
public class Scheduler {

    

    
	@Autowired
	TrainingRepository trainingRepo;
	@Autowired
    private QRCodeServiceImpl qrCodeService;
	
	@Autowired
	IPdfGenaratorService pdfGenerateService;
   
	@Value("${qrcode.directory}")
    private String qrCodeDirectory;

    
	//@Scheduled(cron = "15 * * * * *")
    public void perform() throws Exception {
        log.info("Batch programmé pour tourner toutes les 5 minutes");
        Map<String, Object> data = new HashMap<>();
        
        Training tr = new Training();
        
        for (Training  t : trainingRepo.EndTraining()) {
        	data.put("t", t);
		
        for (User user : t.getLearners()) {
        	data.put("user", user);
        	pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+user.getId()+".pdf");
        	String filePath = qrCodeDirectory + user.getId() + ".png";
            String qrCodeContent = "Simple Solution " + user.getId();
            int width = 400;
            int height = 400;
            //boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
            boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
            if(result) {
               // logger.info("Generate QR code file " + filePath + " successfully");
        	data.put("user", user);
        	
        	///***
        	
        	
            }
        	
            data.put("result", result);
        	pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+user.getId()+".pdf");
		}
        }
    }
}
