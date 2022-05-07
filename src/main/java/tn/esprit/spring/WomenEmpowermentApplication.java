package tn.esprit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WomenEmpowermentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WomenEmpowermentApplication.class, args);
	}

}
