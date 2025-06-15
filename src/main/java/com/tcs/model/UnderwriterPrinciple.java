package com.tcs.model;

import com.tcs.entity.Login;
import com.tcs.entity.Underwriter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom UserDetails implementation for the Underwriter role.
 * Wraps an {@link Underwriter} and exposes authentication and authorization information.
 */
public class UnderwriterPrinciple implements UserDetails {

    private final Underwriter underwriter;

    /**
     * Constructs a new UnderwriterPrinciple using the given underwriter.
     *
     * @param underwriter the underwriter associated with this principal
     */
    public UnderwriterPrinciple(Underwriter underwriter) {
        this.underwriter = underwriter;
    }

    /**
     * Returns the authorities granted to the underwriter.
     *
     * @return a list containing a single {@link SimpleGrantedAuthority} with role "UNDERWRITER"
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_UNDERWRITER"));
    }

    /**
     * Returns the encoded password of the underwriter's login account.
     *
     * @return password from the login entity
     */
    @Override
    public String getPassword() {
        return underwriter.getLogin().getPassword(); // Fixed incorrect string literal
    }

    /**
     * Returns the username for the underwriter (typically their name).
     *
     * @return the underwriter's login username
     */
    @Override
    public String getUsername() {
        return underwriter.getLogin().getUsername(); // Better to use login.username instead of name
    }

    /**
     * Indicates whether the user's account has expired.
     * Always returns true (non-expiring account).
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * Always returns true (account not locked).
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * Always returns true (credentials are valid).
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * Always returns true (enabled by default).
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
