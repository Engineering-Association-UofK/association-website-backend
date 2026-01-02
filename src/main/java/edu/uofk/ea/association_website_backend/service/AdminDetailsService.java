package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.model.AdminModel;
import edu.uofk.ea.association_website_backend.repository.AdminRepo;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepo repo;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;


    @Autowired
    public AdminDetailsService(AdminRepo repo, @Lazy AuthenticationManager authManager, JWTService jwtService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminModel admin = repo.findByUsername(username);

        if(admin == null) {
            throw new UsernameNotFoundException("No Admin with this username found.");
        }

        return User.builder()
                .username(admin.getName())
                .password(admin.getPassword())
                .authorities(admin.getRole().name())
                .build();
    }

    public String Login(AdminModel admin) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getName(), admin.getPassword()));

        if (authentication.isAuthenticated()) return jwtService.generateToken(admin);

        return null;
    }
}
