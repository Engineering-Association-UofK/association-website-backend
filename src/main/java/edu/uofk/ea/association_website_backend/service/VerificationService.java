package edu.uofk.ea.association_website_backend.service;

import edu.uofk.ea.association_website_backend.exceptionHandlers.exceptions.VerificationCodeException;
import edu.uofk.ea.association_website_backend.model.admin.AdminModel;
import edu.uofk.ea.association_website_backend.model.authentication.VerificationCodeModel;
import edu.uofk.ea.association_website_backend.repository.AdminRepo;
import edu.uofk.ea.association_website_backend.repository.VerificationCodeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class VerificationService {

    private final VerificationCodeRepo repo;
    private final AdminRepo adminRepo;
    private final MailService mail;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public VerificationService(VerificationCodeRepo repo, AdminRepo adminRepo, MailService mail, @Lazy PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.adminRepo = adminRepo;
        this.mail = mail;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void verify(AdminModel admin, String code) {
        VerificationCodeModel vCode;
        try {
            vCode = repo.findByAdminId(admin.getId());
        } catch (Exception e) {
            throw new VerificationCodeException("No verification code found for this user.");
        }

        if(vCode.getCreatedAt().isBefore(Instant.now().minusSeconds(600))){
            repo.delete(vCode.getId());
            throw new VerificationCodeException("Code expired");
        }

        if(!passwordEncoder.matches(code, vCode.getCode())){
            throw new VerificationCodeException("Invalid code");
        }

        admin.setIsVerified(true);
        adminRepo.update(admin);
        repo.delete(vCode.getId());
    }

    @Transactional
    public void sendCode(AdminModel admin){
        String code = generateCode();

        
        try {
            VerificationCodeModel existing = repo.findByAdminId(admin.getId());
            repo.delete(existing.getId());
        } catch (Exception e) {
            // Ignore if no previous code exists
        }

        VerificationCodeModel vCode = new VerificationCodeModel();
        vCode.setAdminId(admin.getId());
        vCode.setCode(passwordEncoder.encode(code));
        vCode.setCreatedAt(Instant.now());
        repo.save(vCode);

        mail.sendVerificationCode(admin.getEmail(), code);
    }
    
    @Scheduled(fixedRate = 3600000) // Run every hour
    @Transactional
    public void deleteExpiredCodes() {
        repo.deleteExpiredCodes(Instant.now().minusSeconds(900));
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
