package com.tcs.service;

import com.tcs.entity.Login;
import com.tcs.entity.Underwriter;
import com.tcs.repository.LoginRepository;
import com.tcs.repository.UnderwriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UnderwriterService {

    @Autowired
    private UnderwriterRepository underwriterRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Underwriter addUnderwriter(Underwriter underwriter, String password) {
        // Create login first
        Login login = new Login(
                underwriter.getName(),
                bCryptPasswordEncoder.encode(password),
                "UNDERWRITER"
        );
        loginRepository.save(login);

        // Create underwriter with login reference
        underwriter.setLogin(login);
        return underwriterRepository.save(underwriter);
    }

    public String verifyLogin(Login loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginRequest.getUsername());
        }
        return "fail";
    }

//    public Underwriter register(Underwriter underwriter){
//        underwriter.setPassword(bCryptPasswordEncoder.encode(underwriter.getPassword()));
//        return userRepository.save(underwriter);
//    }

    public Underwriter getUnderwriterById(String name){
        return underwriterRepository.findByName(name);
    }

    public Iterable<Underwriter> getAllUnderwriters(){
        return underwriterRepository.findAll();
    }

//    public String varify(Underwriter underwriter) {
//        Authentication authentication = authenticationManager.authenticate(
//                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
//                        underwriter.getName(), underwriter.getPassword()));
//
//        if (authentication.isAuthenticated())
//            return jwtService.generateToken(underwriter.getName());
//
//        return "fail";
//    }
}
