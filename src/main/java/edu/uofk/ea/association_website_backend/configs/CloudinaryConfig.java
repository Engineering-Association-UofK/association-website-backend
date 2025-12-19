package edu.uofk.ea.association_website_backend.configs;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(System.getenv("CLOUDINARY_URL"));
    }
}
