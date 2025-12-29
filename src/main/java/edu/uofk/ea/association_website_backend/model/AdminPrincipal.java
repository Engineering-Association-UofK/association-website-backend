package edu.uofk.ea.association_website_backend.model;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AdminPrincipal implements UserDetails {

    private final AdminModel admin;

    public AdminPrincipal(AdminModel admin) {
        this.admin = admin;
    }

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public @Nullable String getPassword() {
        return admin.getPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return admin.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //admin.getStatus() == AdminStatus.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //admin.getStatus() == AdminStatus.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //admin.getStatus() == AdminStatus.active;
    }

    @Override
    public boolean isEnabled() {
        return true; //admin.isVerified();
    }
}
