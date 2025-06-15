package com.tcs.service;

import com.tcs.entity.Login;
import com.tcs.entity.Underwriter;
import com.tcs.repository.LoginRepository;
import com.tcs.repository.UnderwriterRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final UnderwriterRepository underwriterRepository;
    private final LoginRepository loginRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public AdminService(UnderwriterRepository userRepository, LoginRepository loginRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.underwriterRepository = userRepository;
        this.loginRepository = loginRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

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

    public String verifyAdminLogin(Login loginRequest) {
        Optional<Login> user = loginRepository.findByUsername(loginRequest.getUsername());
//        if(user.isEmpty() || !"ADMIN".equals(user.get().getRole())) {
//            return "fail";
//        }
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

    public Underwriter getUnderwriterById(String name){
        return underwriterRepository.findByName(name);
    }

    public Underwriter getById(Long id) {
        return underwriterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Underwriter not found with ID: " + id));
    }

    public Iterable<Underwriter> getAllUnderwriters(){
        return underwriterRepository.findAll();
    }

    public String updateUnderwriterPassword(Long id, String newPassword) {
        // Step 1: Find the underwriter by ID
        Underwriter underwriter = underwriterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Underwriter not found with ID: " + id));

        // Step 2: Get associated Login entity
        Login login = underwriter.getLogin();

        if (login == null) {
            throw new RuntimeException("Login record not found for Underwriter ID: " + id);
        }

        // Step 3: Encode new password and update login
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        login.setPassword(encodedPassword);

        // Step 4: Save updated login
        loginRepository.save(login);

        return "Password updated successfully for Underwriter ID: " + id;
    }

    @Transactional
    public void deleteUnderwriterById(Long id) {
        Underwriter underwriter = underwriterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Underwriter not found with ID: " + id));

        // Fetch the managed login entity from DB
        Long loginId = underwriter.getLogin().getId();
        underwriterRepository.delete(underwriter); // delete underwriter first (foreign key constraint)
        loginRepository.deleteById(loginId); // then delete login
    }


}
