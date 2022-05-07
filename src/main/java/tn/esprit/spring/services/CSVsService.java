package tn.esprit.spring.services;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import CSVHelper.CSVHelper;
import tn.esprit.spring.entities.Donationuser;
import tn.esprit.spring.repository.DonationuserRepository;

@Service
public class CSVsService {
	
	
	  @Autowired
	  DonationuserRepository repository;

	  public void save(MultipartFile file) {
	    try {
	      List<Donationuser> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
	      repository.saveAll(tutorials);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }

	  public ByteArrayInputStream load() {
	    List<Donationuser> d = repository.findAll();

	    ByteArrayInputStream in = CSVHelper.tutorialsToCSV(d);
	    return in;
	  }

	  public List<Donationuser> getAllTutorials() {
	    return repository.findAll();
	  }
}
