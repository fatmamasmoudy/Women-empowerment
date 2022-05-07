package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.services.SubjectServiceImpli;
import tn.esprit.spring.services.TagsServiceImpli;

@RestController
@RequestMapping("/Tags")

public class TagsController {
	@Autowired
	SubjectServiceImpli subjectService;
	@Autowired
	TagsServiceImpli tagService;
	
	

    
}
