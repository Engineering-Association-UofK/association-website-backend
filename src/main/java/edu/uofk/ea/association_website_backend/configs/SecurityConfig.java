package edu.uofk.ea.association_website_backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery("select name, password_hash, (verified = true AND status = 'active') from admins where name=?");
        manager.setAuthoritiesByUsernameQuery("select name, role from admins where name=?");

        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
            auth
                .requestMatchers(HttpMethod.GET, "/api/faqs/**", "/api/blogs/**", "/api/gallery/**").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
