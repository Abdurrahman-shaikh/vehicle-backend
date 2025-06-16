package com.tcs.controller;

import com.tcs.entity.Login;
import com.tcs.entity.Underwriter;
import com.tcs.service.UnderwriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling Underwriter operations.
 * Supports registration, login, and fetching underwriter list.
 */
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/underwriter")
public class UnderwriterController {

    @Autowired
    private UnderwriterService underwriterService;

    /**
     * Registers a new underwriter along with their login credentials.
     *
     * @param underwriter Underwriter details
     * @param password    Password for login creation
     * @return the created Underwriter object or error message
     */
    @PostMapping()
    public ResponseEntity<?> addUnderwriter(
            @RequestBody Underwriter underwriter,
            @RequestParam String password
    ) {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!password.matches(pattern)) {
            return ResponseEntity.badRequest().body(
                    "Password must be at least 8 characters long, contain letters, digits, and at least one special character.");
        }

        Underwriter saved = underwriterService.addUnderwriter(underwriter, password);
        return ResponseEntity.ok(saved);
    }

    /**
     * Authenticates an underwriter using login credentials and returns a JWT token.
     *
     * @param login Login credentials (username and password)
     * @return JWT token string if login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<?> verify(@RequestBody Login login) {
        try {
            String token = underwriterService.verifyLogin(login);
            return ResponseEntity.ok(token);
        } catch (Exception ex) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    /**
     * Fetches a list of all registered underwriters.
     *
     * @return list of Underwriter objects
     */
    @GetMapping("/underwriters")
    public ResponseEntity<?> getAllUnderwriters(){
        List<Underwriter> underwriters = (List<Underwriter>) underwriterService.getAllUnderwriters();
        return ResponseEntity.ok(underwriters);
    }

    // Previously used alternate POST endpoints — preserved for reference
    // @PostMapping("/underwriters")
    // public Underwriter addUnderwriter(@RequestBody Underwriter underwriter){
    //     return underwriterService.register(underwriter);
    // }

    // @PostMapping("/login")
    // public String varify(@RequestBody Underwriter underwriter){
    //     return underwriterService.varify(underwriter);
    // }
}
