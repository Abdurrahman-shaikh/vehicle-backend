package com.tcs.service;

import com.tcs.entity.Underwriter;
import com.tcs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UnderwriterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Underwriter register(Underwriter underwriter){
        underwriter.setPassword(bCryptPasswordEncoder.encode(underwriter.getPassword()));
        return userRepository.save(underwriter);
    }

    public Underwriter getUnderwriterById(String name){
        return userRepository.findByName(name);
    }

    public Iterable<Underwriter> getAllUnderwriters(){
        return userRepository.findAll();
    }

    public String varify(Underwriter underwriter) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        underwriter.getName(), underwriter.getPassword()));

        if (authentication.isAuthenticated())
            return jwtService.generateToken(underwriter.getName());

        return "fail";
    }
}
