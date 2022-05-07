package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Advertisement;

public interface IAdService {
	List<Advertisement> retrieveAllAdvertisement();
	//Advertisement addAdvertisement(Advertisement A);
	void deleteAdvertisement(int id);
	Advertisement updateAdvertisement(Advertisement A);
	Advertisement retrieveAdvertisement(int id);
	List<Advertisement> retrieveAllAdvertisementByIntestedBy();
 }
