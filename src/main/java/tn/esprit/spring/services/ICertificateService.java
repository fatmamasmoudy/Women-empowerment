package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Certificate;

public interface ICertificateService {
	
	Certificate addCertificate(Certificate certif);
	
	Certificate updateCertificate(Certificate certif);
	
	void deleteCertificate(int idCertif);
	
	List<Certificate> getAllCertificates();
		
	List<Certificate> getCertificatesByTraining(int TrainingId);

}
