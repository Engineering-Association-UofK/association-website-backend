package edu.uofk.ea.association_website_backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addTemplateResolver(fileTemplateResolver());
        return engine;
    }

    @Bean
    public ITemplateResolver fileTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("templates/"); // TODO: This will probably need to be changed to "/opt/backend/templates/" before deployment
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        resolver.setOrder(1);
        return resolver;
    }
}
