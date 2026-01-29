package edu.uofk.ea.association_website_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// Fix admin roles so every admin can have multiple roles. -- TODO DONE
// Redo endpoints permissions for new admin roles system. -- TODO DONE

// TODO: Fix exceptions to be logged instead of sent to the front end.
// TODO: Fix endpoints permission for open endpoints
// TODO: Check expiration date issue for the digital signature.
// TODO: Add a status field to the certificates & documents tables. (for reasons as revocation)
// TODO: Fix link sent to student email about the certificate.

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class AssociationWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssociationWebsiteBackendApplication.class, args);
	}

}
