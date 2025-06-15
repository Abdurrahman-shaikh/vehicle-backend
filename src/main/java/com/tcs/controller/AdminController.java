package com.tcs.controller;

import com.tcs.entity.Login;
import com.tcs.entity.Underwriter;
import com.tcs.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to handle administrative operations.
 * Provides functionality for:
 * - Admin login
 * - Managing underwriters (register, update password, delete, fetch)
 * - Admin logout
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    private boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(pattern);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            String token = adminService.verifyAdminLogin(login);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/underwriter/register")
    public ResponseEntity<?> registerUnderwriter(@RequestBody Underwriter underwriter,
                                                 @RequestParam String password) {
        if (!isPasswordValid(password)) {
            return ResponseEntity.badRequest().body(
                    "Password must be at least 8 characters long, contain letters, digits, and at least one special character.");
        }
        Underwriter created = adminService.addUnderwriter(underwriter, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/underwriter/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id,
                                            @RequestParam String newPassword) {
        if (!isPasswordValid(newPassword)) {
            return ResponseEntity.badRequest().body(
                    "Password must be at least 8 characters long, contain letters, digits, and at least one special character.");
        }

        try {
            String msg = adminService.updateUnderwriterPassword(id, newPassword);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/underwriter/{id}")
    public ResponseEntity<?> deleteUnderwriter(@PathVariable Long id) {
        try {
            adminService.deleteUnderwriterById(id);
            return ResponseEntity.ok("Underwriter deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/underwriters")
    public ResponseEntity<?> allUnderwriters() {
        List<Underwriter> list = (List<Underwriter>) adminService.getAllUnderwriters();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successful. Please delete the JWT on client side.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/underwriter/{id}")
    public ResponseEntity<?> getUnderwriterById(@PathVariable Long id) {
        try {
            Underwriter underwriter = adminService.getById(id);
            return ResponseEntity.ok(underwriter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Underwriter not found with ID: " + id);
        }
    }
}
