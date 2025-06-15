package com.tcs.model;

import com.tcs.entity.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of Spring Security's {@link UserDetails} interface.
 * This class wraps the {@link Login} entity and provides authentication and authorization information.
 */
public class LoginPrinciple implements UserDetails {

    /**
     * Wrapped Login entity containing credentials and role.
     */
    private final Login login;

    /**
     * Constructs a LoginPrinciple from a Login entity.
     *
     * @param login the Login entity to wrap
     */
    public LoginPrinciple(Login login) {
        this.login = login;
    }

    /**
     * Returns the authorities granted to the user.
     * Adds a role prefix "ROLE_" as expected by Spring Security.
     *
     * @return a list containing a single {@link SimpleGrantedAuthority}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + login.getRole().toUpperCase()));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the hashed password
     */
    @Override
    public String getPassword() {
        return login.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the login username
     */
    @Override
    public String getUsername() {
        return login.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     * Always returns true (non-expiring accounts).
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
     * Indicates whether the user is enabled or disabled.
     * Always returns true (enabled by default).
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
