package tn.esprit.spring.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;

import tn.esprit.spring.entities.Domain;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.Training;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.services.IPdfGenaratorService;
import tn.esprit.spring.services.ITrainingService;
import tn.esprit.spring.services.QRCodeServiceImpl;

import java.util.*;

@RestController
@RequestMapping("/training")
public class TrainingRestController {
	
	public static String text = "Marie was born in Paris.";
	private Logger logger = LoggerFactory.getLogger(TrainingRestController.class);
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	ITrainingService trainingServ;
	
	@Autowired
    private QRCodeServiceImpl qrCodeService;
	
	@Autowired
	IPdfGenaratorService pdfGenerateService;
	
	 @Value("${qrcode.directory}")
	    private String qrCodeDirectory;
	 
	 @Value("${file.upload-dir}")
		String FILE_DIRECTORY;
	
	
//	//http://localhost:8083/women/training/add
	@PostMapping("/save")
	@ResponseBody
		
	public Training saveTraining(@RequestBody Training t)
	{							
		return trainingServ.addTraining(t);
	}
	
	@PutMapping("/addCours")
	@ResponseBody
		
	public Training saveTraining(@RequestParam int id, @RequestParam("cours") MultipartFile file)
	
	{							
		Training t = trainingServ.findById(id);
		File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
		try {
			myFile.createNewFile();
			FileOutputStream fos =new FileOutputStream(myFile);
			fos.write(file.getBytes());			
			t.setCours(file.getOriginalFilename());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return trainingServ.updateTraining(t);
	}
	 
	 
	//http://localhost:8083/women/training/add
		@PostMapping("/add")
		@ResponseBody
		public ResponseEntity<?> addTraining(@RequestHeader (name="Authorization") String token, @RequestParam("subject") String subject, @RequestParam("cours") MultipartFile file)
		
		//public Training addTraining(@RequestBody Training t, @RequestParam("cours") MultipartFile file)
		{
			if(token==null){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
			}
			String email;
			try{
				token=token.replace("Bearer ", "");
				email = jwtUtils.getUserNameFromJwtToken(token);
			if(email==null){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
			}
			}catch(Exception e){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
			}
			
			Training t = new Training();
			File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
			try {
				myFile.createNewFile();
				FileOutputStream fos =new FileOutputStream(myFile);
				fos.write(file.getBytes());
				
				t.setSubject(subject);
				
				t.setCours(file.getOriginalFilename());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(trainingServ.addTraining(t)!=null)
				 return ResponseEntity.ok(new MessageResponse("success"));
			 else
				 return ResponseEntity.ok(new MessageResponse("echec !"));
			
					
			
		}
		
		
	
	//http://localhost:8083/women/training/update
	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> updateTraining(@RequestHeader (name="Authorization") String token, @RequestBody Training t) {
		if(token==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
		}
		String email;
		try{
			token=token.replace("Bearer ", "");
			email = jwtUtils.getUserNameFromJwtToken(token);
		if(email==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		}catch(Exception e){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		if(trainingServ.updateTraining(t)!=null)
			return ResponseEntity.ok(new MessageResponse("success"));
		 else
			 return ResponseEntity.ok(new MessageResponse("echec !"));
			
	}
	
	//http://localhost:8083/women/training/evaluate/1
		@PutMapping("/evaluate/{idTraining}")
		@ResponseBody
		public ResponseEntity<?> evaluateTraining(@RequestHeader (name="Authorization") String token, @PathVariable("idTraining") int idTraining, @RequestBody Evaluation ev) {
			if(token==null){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
			}
			String email;
			try{
				token=token.replace("Bearer ", "");
				email = jwtUtils.getUserNameFromJwtToken(token);
			if(email==null){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
			}
			}catch(Exception e){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
			}
			if(ev!=null)
			{
				trainingServ.evaluateTraining(idTraining, ev);
				return ResponseEntity.ok(new MessageResponse("success"));
			}
			 else
				 return ResponseEntity.ok(new MessageResponse("echec !"));
		}
	
	//http://localhost:8083/women/training/assignQuizToTraining/1/1
		@PutMapping("/assignQuizToTraining/{idTraining}/{idQuiz}")
		@ResponseBody
		public ResponseEntity<?> assignQuizToTraining(@RequestHeader (name="Authorization") String token,@PathVariable("idTraining") int idTraining, @PathVariable("idQuiz") int idQuiz) {
			if(token==null){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
			}
			String email;
			try{
				token=token.replace("Bearer ", "");
				email = jwtUtils.getUserNameFromJwtToken(token);
			if(email==null){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
			}
			}catch(Exception e){
				return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
			}
			
			trainingServ.assignQuizToTraining(idTraining, idQuiz);
			return ResponseEntity.ok(new MessageResponse("success"));
		}
		
	//http://localhost:8083/women/training/participe/1/1
	@PostMapping("/participe/{idLearner}/{idTraining}")
	@ResponseBody
	public ResponseEntity<?> participe(@RequestHeader (name="Authorization") String token, @PathVariable("idLearner") int idLearner, @PathVariable("idTraining") int idTraining) {
		if(token==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
		}
		String email;
		try{
			token=token.replace("Bearer ", "");
			email = jwtUtils.getUserNameFromJwtToken(token);
		if(email==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		}catch(Exception e){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		
		trainingServ.participe(idLearner, idTraining);
		return ResponseEntity.ok(new MessageResponse("success"));
	}	
	
	@DeleteMapping("/remove/{id}")
	@ResponseBody
	public ResponseEntity<?> removeTraining(@RequestHeader (name="Authorization") String token, @PathVariable("id") int id) {
		if(token==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
		}
		String email;
		try{
			token=token.replace("Bearer ", "");
			email = jwtUtils.getUserNameFromJwtToken(token);
		if(email==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		}catch(Exception e){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		
		trainingServ.deleteTraining(id);
		return ResponseEntity.ok(new MessageResponse("success"));
		
	}
	
	@GetMapping("/retrieveById/{id}")
	@ResponseBody
	public Training retrieveById(@PathVariable("id") int id) {
		Map<String, Object> data = new HashMap<>();
        Training t = trainingServ.findById(id);
        
        data.put("t", t);

        for (User user : t.getLearners()) {
        	data.put("user", user);
        	pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+t.getId()+user.getId()+".pdf");
        	String filePath = qrCodeDirectory + user.getId() + ".png";
            String qrCodeContent = "Simple Solution " + user.getId();
            int width = 400;
            int height = 400;
            //boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
            boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
            if(result) {
                logger.info("Generate QR code file " + filePath + " successfully");
        	data.put("user", user);
        	
        	///***
        	
        	
            }
        	
            data.put("result", result);
        	pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+user.getId()+".pdf");
		}
            
		return trainingServ.findById(id);
	}
	
	@GetMapping("/retrieve-Subject/{subj}")
	@ResponseBody
	public List<Training> retrieveBySubject(@PathVariable("subj") String subj){
		return trainingServ.findBySubjectContains(subj);
	}
	
	@GetMapping("/retrieve-all-training")
	@ResponseBody
	public List<Training> getClients() {
		return trainingServ.getAllTrainings();
	}
	
	@GetMapping("/retrieveByTrainer/{trainerName}")
	@ResponseBody
	public List<Training> retrieveByTrainer(@PathVariable("trainerName") String trainerName){
		return trainingServ.findByTrainerName(trainerName);
	}
	
//	@GetMapping("/generateQRCode")
//    public void generateQRCode(String qrContent, HttpServletResponse response) throws IOException {
//        response.setContentType("image/png");
//        byte[] qrCode = qrCodeService.generateQRCode(qrContent, 500, 500, 1);
//        OutputStream outputStream = response.getOutputStream();
//        outputStream.write(qrCode);
//    }
	
	 @PostMapping("/showQRCode")
	    public String showQRCode(String qrContent, Model model) {
	        model.addAttribute("qrCodeContent", "/generateQRCode?qrContent=" + qrContent);
	        
	        return "show-qr-code";
	    }
	 
	 

	 @GetMapping("/QRCode/{id}")
	    public void QRCode(String qrContent, HttpServletResponse response, @PathVariable("id") int id) throws IOException {
	        response.setContentType("image/png");
	        
	        
	        Map<String, Object> data = new HashMap<>();
	        Training t = trainingServ.findById(id);
	        
	        data.put("t", t);

	        for (User user : t.getLearners()) {
	        	String filePath = qrCodeDirectory + user.getId() + ".png";
	            String qrCodeContent = "Simple Solution " + user.getId();
	            int width = 400;
	            int height = 400;
	            //boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
	            boolean result = qrCodeService.generateQRCode(user.getEmail()+' '+t.getSubject(), filePath, width, height);
	            if(result) {
	                logger.info("Generate QR code file " + filePath + " successfully");
	        	data.put("user", user);
	        	
	        	///***
	        	
	        	
	            }
	        	
	            data.put("result", result);
	        	pdfGenerateService.generatePdfFile("Certificate", data, "Certificate"+user.getId()+".pdf");
			}
	        System.out.println(t.getLearners().size());
	    }
	 
	 
	 @GetMapping("/searchTraining/{userId}/{str}")
		@ResponseBody
		public List<Training> searchTraining(@PathVariable("userId") int userId,@PathVariable("str") String str) throws Exception{
			return trainingServ.searchTraining(userId ,str);
		}
	 
	 
	 @GetMapping("/searchTrainingByTiming/{timing}")
		@ResponseBody
		public List<Training> searchTrainingByTiming(@PathVariable("timing") Double timing){
			return trainingServ.searchTrainingByTiming(timing);
		}
	 
	 @GetMapping("/suggestion/{userId}")
		@ResponseBody
		public List<Training> suggestion(@PathVariable("userId") int userId){
			return trainingServ.suggestion(userId);
		}
	 
	 

}
