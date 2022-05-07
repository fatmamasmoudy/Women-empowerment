package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.*;

public interface IUserService {
	
	List<User> retrieveAllUsers();

	public User addUser(User u);

	void deleteUser(long id);

	User updateUser(User c);

	User retrieveUser (long id);
	
	User findById(long id);


}
