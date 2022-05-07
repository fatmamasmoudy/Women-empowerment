package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Subject;


public interface ISubjectService {

	List<Subject> retrieveAllSubject();
	//Subject addSubject(Subject S) ;
//    int AddVue(Subject S) ;
	void deleteSubject(int id);
	Subject updateSubject(Subject c);
	Subject retrieveSubject(int id);
	List<Subject> retrieveSubjectByDate();
	//void deleteSubjectExist(int id);
}
	
