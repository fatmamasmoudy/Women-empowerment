package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.SmsRequest;
import tn.esprit.spring.services.SmsService;

@RestController
@RequestMapping("api/v1/sms")

public class SmsController {
	 private final SmsService smsService;

	 
	    @Autowired
	    public SmsController(SmsService smsService) {
	        this.smsService = smsService;
	    }

	    @PostMapping
	  //  @Scheduled(cron = "*/10 * * * * *")
	    public void sendSms( @RequestBody SmsRequest smsRequest) {
	    	smsService.sendSms(smsRequest);
	    }


}
