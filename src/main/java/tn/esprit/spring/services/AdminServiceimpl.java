package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Admin;
import tn.esprit.spring.repository.AdminRepository;
import tn.esprit.spring.repository.UserRepository;
@Service
public class AdminServiceimpl implements IAdminService {
	@Autowired
	AdminRepository adminRepository;

	@Override
	public Admin retrieveAdmin(long id) {
		// TODO Auto-generated method stub
		return adminRepository.findById(id).get();
	}
}
