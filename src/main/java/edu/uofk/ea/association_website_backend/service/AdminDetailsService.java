package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UnauthorizedException;
import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.UserAlreadyExistsException;
import edu.uofk.ea.association_website_backend.model.admin.*;
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
                .disabled(admin.getStatus() == AdminStatus.deactivated || !admin.getIsVerified())
                .accountLocked(admin.getStatus() == AdminStatus.locked)
                .build();
    }

    public String login(LoginRequest admin) {
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
        System.out.println(admin);
        if (admin.getIsVerified()) throw new UnauthorizedException("Email already verified");

        verificationService.sendCode(admin);
    }

    public void verify(String name, String code) {
        AdminModel admin = repo.findByUsername(name);
        if (admin == null) throw new UsernameNotFoundException("Admin with this username not found");

        verificationService.verify(admin, code);
    }

    public void updateProfile(AdminRequest request) {
        AdminModel existing = repo.findByUsername(request.getName());
        if (existing == null) throw new UsernameNotFoundException("User not found");

        // Validate Roles
        if (request.getRoles() != null && request.getRoles().contains(AdminRole.ROLE_SUPER_ADMIN)) {
            throw new IllegalArgumentException("Cannot add super admin role manually");
        }

        // Validate & Update Password
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Validate & Update Email
        String newEmail = request.getEmail();
        if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(existing.getEmail())) {
            if (repo.findByEmail(newEmail) != null) throw new UserAlreadyExistsException("User with this email already exists");
            existing.setEmail(newEmail);
            existing.setIsVerified(false);
        }

        // Update Roles
        if (request.getRoles() != null && !request.getRoles().isEmpty() && !existing.getRoles().equals(request.getRoles())) {
            existing.setRoles(request.getRoles());
        }
        
        repo.update(existing);
    }

    public void add(AdminRequest admin) {
        if (repo.findByUsername(admin.getName()) != null)                   throw new UserAlreadyExistsException("Admin with this username already exists");
        
        if (admin.getEmail() == null || admin.getEmail().isEmpty())         throw new IllegalArgumentException("Need email to add admin");
        if (repo.findByEmail(admin.getEmail()) != null)                     throw new UserAlreadyExistsException("Admin with this email already exists");

        if (admin.getPassword() == null || admin.getPassword().isEmpty())   throw new IllegalArgumentException("Password does not exist in request, make a default one.");
        
        if (admin.getRoles() == null || admin.getRoles().isEmpty())         throw new IllegalArgumentException("Roles cannot be null or empty");
        if (admin.getRoles().contains(AdminRole.ROLE_SUPER_ADMIN))          throw new IllegalArgumentException("Cannot add super admin role manually");

        AdminModel newAdmin = new AdminModel(
                admin.getName(),
                admin.getEmail(),
                passwordEncoder.encode(admin.getPassword()),
                admin.getRoles()
        );

        repo.save(newAdmin);
    }

    public void delete(int id) {
        if (repo.findById(id) == null) throw new UsernameNotFoundException("Admin with this id not found");
        AdminModel admin = repo.findById(id);
        admin.setStatus(AdminStatus.deactivated);
        repo.update(admin);
    }

    public AdminModel get(int id) {
        if (repo.findById(id) == null) throw new UsernameNotFoundException("Admin with this id not found");
        AdminModel admin = repo.findById(id);
        admin.setPassword(null);

        return admin;
    }

    public List<AdminModel> getAll() {
        List<AdminModel> admins = repo.getAll();
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

    public void updatePassword(UpdatePasswordRequest request, String username) {
        AdminModel admin = repo.findByUsername(username);
        if (admin == null) throw new UsernameNotFoundException("Admin with this username not found");

        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) throw new BadCredentialsException("Old password is incorrect");
        if (!request.getNewPassword().equals(request.getConfirmPassword())) throw new IllegalArgumentException("New password and confirm password do not match");
        if (request.getNewPassword().equals(request.getOldPassword())) throw new IllegalArgumentException("New password cannot be the same as the old password");

        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repo.update(admin);
    }

    public void updateEmail(UpdateEmailRequest request, String username) {
        AdminModel admin = repo.findByUsername(username);
        if (admin == null) throw new UsernameNotFoundException("Admin with this username not found");

        if (repo.findByEmail(request.getNewEmail()) != null) throw new UserAlreadyExistsException("Admin with this email already exists");
        if (admin.getEmail().equals(request.getNewEmail())) throw new IllegalArgumentException("New email cannot be the same as the old email");

        admin.setEmail(request.getNewEmail());
        admin.setIsVerified(false);
        repo.update(admin);
    }

    public int getId(String name) {
        if (repo.findByUsername(name) == null) throw new UsernameNotFoundException("Admin with this username not found");
        AdminModel admin = repo.findByUsername(name);

        return admin.getId();
    }
}
