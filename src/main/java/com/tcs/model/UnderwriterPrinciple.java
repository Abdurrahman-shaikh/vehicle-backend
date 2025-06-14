package com.tcs.model;

import com.tcs.entity.Underwriter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UnderwriterPrinciple implements UserDetails {

    private Underwriter underwriter;

    public UnderwriterPrinciple(Underwriter underwriter) {
        this.underwriter = underwriter;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("UNDERWRITER"));
    }

    @Override
    public String getPassword() {
        return underwriter.getPassword();
    }

    @Override
    public String getUsername() {
        return underwriter.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}