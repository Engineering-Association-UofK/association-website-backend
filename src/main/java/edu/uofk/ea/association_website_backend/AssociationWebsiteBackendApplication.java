package edu.uofk.ea.association_website_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class AssociationWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssociationWebsiteBackendApplication.class, args);
	}

}
