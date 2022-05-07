package tn.esprit.spring.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entities.CSVHelper;
import tn.esprit.spring.entities.CV;
import tn.esprit.spring.repository.CvsRepository;

@Service
public class CsvService {
	@Autowired
	  CvsRepository cvsrepository;
	  
	  public ByteArrayInputStream load() {
	    List<CV> cvs = (List<CV>) cvsrepository.findAll();
	    ByteArrayInputStream in = CSVHelper.CvToCSV(cvs);
	    return in;
	  }
	  public void save(MultipartFile file) {
		    try {
		      List<CV> cvs = CSVHelper.csvToCv(file.getInputStream());
		  	
		      cvsrepository.saveAll(cvs);
		    } catch (IOException e) {
		      throw new RuntimeException("fail to store csv data: " + e.getMessage());
		    }
		  }
		  public List<CV> getAllTutorials() {
		    return (List<CV>) cvsrepository.findAll();
		  }
	/*public void dowloadFile (PrintWriter printWriter ,List<CV> cv){
      		
	}
	
	*/
}
