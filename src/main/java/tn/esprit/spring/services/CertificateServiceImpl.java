package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.repository.CertificateRepository;

@Service
public class CertificateServiceImpl implements ICertificateService {
	@Autowired
	CertificateRepository certifRepo;

	@Override
	public Certificate addCertificate(Certificate certif) {
		return certifRepo.save(certif);				
	}

	@Override
	public Certificate updateCertificate(Certificate certif) {
		return certifRepo.save(certif);	
	}

	@Override
	public void deleteCertificate(int idCertif) {
		certifRepo.deleteById(idCertif);
		
	}
	
	@Override
	public List<Certificate> getAllCertificates() {
		return (List<Certificate>) certifRepo.findAll();
	}

	@Override
	public List<Certificate> getCertificatesByTraining(int trainingId) {
		return certifRepo.findByTraining(trainingId);
	}
	

}
