package com.tcs.service;

import com.tcs.entity.Underwriter;
import com.tcs.model.UnderwriterPrinciple;
import com.tcs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Underwriter underwriter = userRepository.findByName(username);
        if (underwriter == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UnderwriterPrinciple(underwriter);
    }
}

