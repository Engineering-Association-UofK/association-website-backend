package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UserAlreadyExistsException;
import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import edu.uofk.ea.association_website_backend.model.admin.AdminRole;
import edu.uofk.ea.association_website_backend.model.admin.AdminStatus;
import edu.uofk.ea.association_website_backend.repository.AdminRepo;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepo repo;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;

    @Autowired
    public AdminDetailsService(AdminRepo repo, @Lazy AuthenticationManager authManager, @Lazy JWTService jwtService, @Lazy PasswordEncoder passwordEncoder, @Lazy VerificationService verificationService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.verificationService = verificationService;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminModel admin = repo.findByUsername(username);

        if(admin == null) {
            throw new UsernameNotFoundException("No Admin with this username found.");
        }

        String[] authorities = admin.getRoles().stream()
                .map(Enum::name)
                .toArray(String[]::new);

        return User.builder()
                .username(admin.getName())
                .password(admin.getPassword())
                .authorities(authorities)
                .disabled(admin.getStatus() == AdminStatus.deactivated)
                .accountLocked(admin.getStatus() == AdminStatus.locked)
                .build();
    }

    public String login(AdminModel admin) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getName(), admin.getPassword()));

        AdminModel dbAdmin = repo.findByUsername(admin.getName());
        if (!dbAdmin.getIsVerified()) {
            throw new UnauthorizedException("Account not verified");
        }

        if (authentication.isAuthenticated()) return jwtService.generateAdminToken(dbAdmin);

        return null;
    }

    public void sendCode(String name){
        AdminModel admin = repo.findByUsername(name);
        if (admin == null) throw new UsernameNotFoundException("Admin with this username not found");
        if (admin.getIsVerified()) throw new UnauthorizedException("Email already verified");

        verificationService.sendCode(admin);
    }

    public void verify(String name, String code) {
        AdminModel admin = repo.findByUsername(name);
        if (admin == null) throw new UsernameNotFoundException("Admin with this username not found");

        verificationService.verify(admin, code);
    }

    public void updateProfile(AdminModel request) {
        AdminModel existing = repo.findByUsername(request.getName());
        if (existing == null) throw new UsernameNotFoundException("User not found");

        if (request.getName() != null) existing.setName(request.getName());
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
            existing.setIsVerified(false);
        }
        if (request.getPassword() != null) existing.setPassword(passwordEncoder.encode(request.getPassword()));
        
        repo.update(existing);
    }

    public void add(AdminModel admin) {
        if (admin.getName() == null) throw new IllegalArgumentException("Name cannot be null");
        if (repo.findByUsername(admin.getName()) != null) throw new UserAlreadyExistsException("Admin with this username already exists");
        if (admin.getEmail() == null) throw new IllegalArgumentException("Email cannot be null");
        if (admin.getPassword() == null) throw new IllegalArgumentException("Password cannot be null");

        if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
            admin.setRoles(new HashSet<>());
            admin.getRoles().add(AdminRole.ROLE_PAPER_VIEWER);
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setStatus(AdminStatus.pending);
        admin.setIsVerified(false);
        admin.setCreatedAt(java.time.Instant.now());
        repo.save(admin);
    }

    public void delete(int id) {
        if (repo.findById(id) == null) throw new UsernameNotFoundException("Admin with this id not found");
        repo.delete(id);
    }

    public AdminModel get(int id) {
        if (repo.findById(id) == null) throw new UsernameNotFoundException("Admin with this id not found");
        AdminModel admin = repo.findById(id);
        admin.setPassword(null);

        return admin;
    }

    public List<AdminModel> getAll() {
        List<AdminModel> admins = repo.getAllActive();
        admins.addAll(repo.getAllPending());
        for (AdminModel admin : admins) {
            admin.setPassword(null);
        }
        return admins;
    }

    public String getName(int id) {
        if (repo.findById(id) == null) throw new UsernameNotFoundException("Admin with this id not found");
        AdminModel admin = repo.findById(id);

        return admin.getName();
    }
}
