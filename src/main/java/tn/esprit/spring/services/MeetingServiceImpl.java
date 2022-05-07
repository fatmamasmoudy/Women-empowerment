package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Meeting;
import tn.esprit.spring.repository.MeetingRepository;

@Service
public class MeetingServiceImpl implements IMeetingService {
	@Autowired
	MeetingRepository meetingRepository;
	@Override
	public List<Meeting> retrieveAllMeetings() {
		return (List<Meeting>) meetingRepository.findAll();
	}
	
	@Override
	public Meeting addMeeting(Meeting u) {
		meetingRepository.save(u);
		return u;
	}

	@Override
	public void deleteMeeting (long id) {
		meetingRepository.deleteById(id);
	}

	@Override
	public Meeting updateMeeting(Meeting u) {
		meetingRepository.save(u);
		return u;
	}

	@Override
	public Meeting retrieveMeeting(long id) {
		Meeting u = meetingRepository.findById(id).get();
		return u;
	}


}
