package tn.esprit.spring.services;

import tn.esprit.spring.entities.Admin;
import tn.esprit.spring.entities.User;

public interface IAdminService {
	Admin retrieveAdmin (long id);
}
