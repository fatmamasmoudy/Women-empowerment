package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.*;

public interface IMeetingService {
	
	List<Meeting> retrieveAllMeetings();

	Meeting addMeeting(Meeting m);

	void deleteMeeting(long id);

	Meeting updateMeeting(Meeting m);

	Meeting retrieveMeeting(long id);


}
