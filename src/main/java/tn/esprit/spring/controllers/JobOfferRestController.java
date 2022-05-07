package tn.esprit.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import tn.esprit.spring.entities.JobOffer;
import tn.esprit.spring.entities.JobPage;
import tn.esprit.spring.entities.JobSearchCriteria;
import tn.esprit.spring.services.JobOfferService;

@RestController
@RequestMapping("/JobOffer")
public class JobOfferRestController {

	
	@Autowired
	JobOfferService jobOfferServ;
	/*
	@PostMapping("/addJob")
	public void AddJobOffer( @RequestBody JobOffer jobOffer){
		jobOfferServ.AddJobOffer(jobOffer);
		
	}
*/
    @PostMapping("/addJob")
    public ResponseEntity<JobOffer> addJob(@RequestBody JobOffer jobOffer){
        return new ResponseEntity<>(jobOfferServ.addJob(jobOffer), HttpStatus.OK);
    }
	
	
	/* @RequestMapping(value = "/JobOffers", method = RequestMethod.GET)
	    public String list(JobOffer jobOffer) {
	        jobOffer.addAttribute("jobOffers", jobOfferServ.listAllJobOffers());
	        System.out.println("Returning JobOffers:");
	        return "JobOffers";
	    }
	*/
	/*
    @RequestMapping("JobOffer/edit/{id}")
    public String edit(@PathVariable Integer id, JobOffer jobOffer) {
    	//JobOffer.addAttribute("JobOffer", jobOfferServ.getJobOfferById(id));
        return jobOfferServ.edit(id,jobOffer);;
    }
*/
   

   
    /**
     * Delete JobOffer by its id.
     *
     *
     * 
     */
	@DeleteMapping("JobOffer/delete/{id}")
    public String delete(@PathVariable Integer id) {
    	jobOfferServ.deleteJobOffer(id);
        return "redirect:/JobOffers";
    }
   /*
    @PutMapping(value = "/UpdateJobOffer/{idJOffer}")
    public ResponseEntity<?> updateJobOffer(@PathVariable("idJOffer") Integer idJOffer, @RequestBody JobOffer jobOffer) {
    	jobOffer.getIdJOffer();
        JobOffer updateJobOffer = jobOfferServ.updateJobOffer(jobOffer);
        return new ResponseEntity<Object>(updateJobOffer, HttpStatus.OK);
    }
    */

    @PutMapping("/updateJob/{id}")
	@ResponseBody
	public JobOffer updateJobOffer(@RequestBody JobOffer jobOffer) {
		return jobOfferServ.updateJobOffer(jobOffer);
	}
	
	
    @GetMapping("/AllJobsOffer")
	@ResponseBody
	public List<JobOffer> getJobs() {
		return jobOfferServ.getAllJobs();
    }
    
    
    @GetMapping("/GETJobsOffer")
    public ResponseEntity<Page<JobOffer>> getEmployees(JobPage jobPage,
                                                       JobSearchCriteria jobSearchCriteria){
        return new ResponseEntity<>(jobOfferServ.getJobs(jobPage, jobSearchCriteria),
                HttpStatus.OK);
    }

}
