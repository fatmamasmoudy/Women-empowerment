package tn.esprit.spring.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.*;
import tn.esprit.spring.payload.MessageResponse;
import tn.esprit.spring.repository.PartnerRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.JwtUtils;
import tn.esprit.spring.services.IMeetingService;
import tn.esprit.spring.services.IPartnerService;

@RestController
@RequestMapping("/Meeting")
public class MeetingRestController {
	@Autowired
	IMeetingService meetingService;
	@Autowired
	UserRepository userrepository;
	@Autowired
	PartnerRepository partnerrepository;
	@Autowired
	IPartnerService partnerservice;
	@Autowired
	JwtUtils jwtUtils;
	
	// http://localhost:8083/SpringMVC/Meeting/retrieve-all-meetings
	@GetMapping("/retrieve-all-meetings")
	@ResponseBody
	public List<Meeting> getMeetings() {
		List<Meeting> listMeetings = meetingService.retrieveAllMeetings();
		return listMeetings;
	}
	
	// http://localhost:8083/SpringMVC/Meeting/retrieve-meeting/8
	@GetMapping("/retrieve-meeting/{meeting-id}")
	@ResponseBody
	public Meeting retrieveMeeting(@PathVariable("meeting-id") Long meetingId) 
	{
		return meetingService.retrieveMeeting(meetingId);
	}

	
	@PostMapping("/add-meeting")
	@ResponseBody
	public ResponseEntity<?> addmeeting(@RequestBody Meeting c, @RequestHeader (name="Authorization") String token)
	  { 
		if(token==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not connected" ));
		}
		String email;
		try{
			token=token.replace("Bearer ", "");
			email = jwtUtils.getUserNameFromJwtToken(token);
		if(email==null){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		}catch(Exception e){
			return ResponseEntity.badRequest().body(new MessageResponse("You are not authorized" ));	
		}
		
		User user=userrepository.findByEmail(email).get();

		
		
		c.setValidity(Etat.pending);
		c.setUser(user);
		Meeting meet = meetingService.addMeeting(c);
		
		return ResponseEntity.ok(meet);
      }
	
	// http://localhost:8083/SpringMVC/Meeting/remove-meeting/{meeting-id}
	@DeleteMapping("/remove-meeting/{meeting-id}")
	@ResponseBody
	public void removeMeeting(@PathVariable("meeting-id") Long meetingId)
	{
		meetingService.deleteMeeting(meetingId);
	}

	
	
	
	
	
	// http://localhost:8083/SpringMVC/Meeting/get-meetingbyuser
	@GetMapping("/getmeetingbyuser/{id}")
	    public Set<Meeting> getallmeetingsbyuser(@PathVariable(value = "id") Long iduser) {
	        Optional<User> user = userrepository.findById(iduser);
	       return user.get().getMeetings();
	        
	    }
	
	// http://localhost:8083/SpringMVC/Meeting/get-meetingbypartner
	@GetMapping("/getmeetingbypartner/{id}")
    public Set<Meeting> getallmeetingsbypartner(@PathVariable(value = "id") Long idpartner) {
        Optional<Partner> partner = partnerrepository.findById(idpartner);
       return partner.get().getMeetings();
        
    }
	
	// http://localhost:8083/SpringMVC/Meeting/get-meetingbypartner
	 @PutMapping("/affectmeetingtopartner/{id}/{idm}")
	    public Meeting affectmeetingtopartner(@PathVariable(value = "id") Long idpartner ,@PathVariable(value = "idm") Long idmeeting) {
		 Partner p= partnerservice.retrievePartner(idpartner);
		 Meeting m=meetingService.retrieveMeeting(idmeeting);
		m.setPartner(p);
		 
		return meetingService.updateMeeting(m);
	        
	    }
	// http://localhost:8083/SpringMVC/Meeting/getdisponiblepartnersbydate
		@GetMapping("/getdisponiblepartnersbydate/{date}")
	    public List<Partner> getdisponiblepartnersbydate(@PathVariable(value = "date") String date) {
			Date datemeeting=new Date();
			
			try {
				datemeeting = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				
				
			} catch (ParseException e) {
				
			}
			
			List<Partner>partners=partnerservice.retrieveAllPartners();
			
			List<Meeting>meetings=meetingService.retrieveAllMeetings();
			List<Partner>notavailablepartners=new ArrayList<Partner>();
			
			for(Meeting m :meetings){
				if(m.getDate().getDay()==datemeeting.getDay() && m.getDate().getMonth()==datemeeting.getMonth() && m.getDate().getYear()==datemeeting.getYear())
				{
					
					notavailablepartners.add(m.getPartner());
				}
			}
	        
    partners.removeAll(notavailablepartners);
    return partners;
	    }
		
		
		// http://localhost:8083/SpringMVC/Meeting/validatemeeting/1/0
		@ResponseBody
		@PutMapping("/validatemeeting/{id}/{decision}")
		public Meeting validatemeeting(@PathVariable(value = "id")  Long id,@PathVariable(value = "decision") int decision)
		{   Meeting m= meetingService.retrieveMeeting(id);
			if(decision==1){
				m.setValidity(Etat.valid);
			}else
				m.setValidity(Etat.refused);	
				
			return meetingService.updateMeeting(m);
		}
		
	
	 
}



