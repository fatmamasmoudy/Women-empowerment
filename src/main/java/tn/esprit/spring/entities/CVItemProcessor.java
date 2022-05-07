package tn.esprit.spring.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class CVItemProcessor implements ItemProcessor<CV, CV> {
	private static final Logger log = LoggerFactory.getLogger(CVItemProcessor.class);

	@Override
	public CV process(final CV cv) throws Exception {
		  final String profileTitle = cv.getProfileTitle().toUpperCase();
	        final String aboutMe = cv.getAboutMe().toUpperCase();
	        final String internetSite = cv.getInternetSite().toUpperCase();
	        final String drivingLesence = cv.getDrivingLesence().toUpperCase();
	        final String linkdIn = cv.getLinkdIn().toUpperCase();
	        final String profileDescription = cv.getProfileDescription().toUpperCase();
	        final String centerOfInterest = cv.getCenterOfInterest().toUpperCase();
	 //       final String file = cv.getFile().toUpperCase();
	       

 
	        final CV transformedCV = new CV(profileTitle, aboutMe,internetSite,drivingLesence,linkdIn,profileDescription,centerOfInterest);

	        log.info("Converting (" + cv + ") into (" + transformedCV + ")");

	        return transformedCV;
	    }
	}


