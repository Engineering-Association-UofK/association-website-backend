package edu.uofk.ea.association_website_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cloudinary.*;

@SpringBootApplication
public class AssociationWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssociationWebsiteBackendApplication.class, args);

        Cloudinary cloudinary = new Cloudinary(System.getenv("CLOUDINARY_URL"));
	}

}
