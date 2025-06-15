package com.tcs.controller;

import com.tcs.dto.VehicleRequest;
import com.tcs.entity.Vehicle;
import com.tcs.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Controller for vehicle-related operations.
 * Only users with the UNDERWRITER role can access these endpoints.
 */
@RestController
@RequestMapping("/api/vehicle")
@PreAuthorize("hasRole('UNDERWRITER')")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Registers a new vehicle under the authenticated underwriter.
     *
     * @param request        Vehicle data transfer object
     * @param authentication Authenticated user info (JWT-based)
     * @return success message
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerVehicle(@RequestBody VehicleRequest request, Authentication authentication) {
        String username = authentication.getName(); // Extract underwriter username from JWT
        vehicleService.registerVehicle(request, username);
        return ResponseEntity.ok("Vehicle registered successfully");
    }

    /**
     * Returns all vehicles registered by the currently authenticated underwriter.
     *
     * @param authentication JWT-based authentication info
     * @return list of vehicles
     */
    @GetMapping("/view-my-vehicles")
    public ResponseEntity<List<Vehicle>> viewMyVehicles(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(vehicleService.getVehiclesByUnderwriter(username));
    }

    /**
     * Renews a policy for a vehicle if it belongs to the authenticated underwriter.
     *
     * @param policyId       ID of the policy to renew
     * @param claimStatus    New claim status for the renewal
     * @param authentication JWT authentication to extract underwriter username
     * @return renewed vehicle object
     */
    @PutMapping("/renew/{policyId}")
    public ResponseEntity<Vehicle> renewPolicy(@PathVariable Long policyId,
                                               @RequestParam String claimStatus,
                                               Authentication authentication) {
        String username = authentication.getName();
        Vehicle renewed = vehicleService.renewPolicy(policyId, claimStatus, username);
        return ResponseEntity.ok(renewed);
    }

    /**
     * Updates the policy type of a vehicle. Allowed only for policies owned by the underwriter.
     *
     * @param policyId        ID of the vehicle policy to update
     * @param newPolicyType   New policy type value
     * @param authentication  JWT authentication to verify ownership
     * @return success message
     */
    @PutMapping("/update-policy-type/{policyId}")
    public ResponseEntity<String> updatePolicyType(
            @PathVariable Long policyId,
            @RequestParam String newPolicyType,
            Authentication authentication) {

        String username = authentication.getName();
        vehicleService.updatePolicyType(policyId, newPolicyType, username);
        return ResponseEntity.ok("Policy type updated successfully");
    }

    /**
     * Fetches all registered vehicles across all underwriters.
     * Can be used for debugging or admin views.
     *
     * @return list of all vehicles
     */
    @GetMapping("/get-all-vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllPolicies());
    }

    /**
     * Retrieves a specific vehicle policy by its ID.
     *
     * @param policyId Policy ID
     * @return Vehicle object
     */
    @GetMapping("/{policyId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long policyId) {
        return ResponseEntity.ok(vehicleService.getPolicyById(policyId));
    }
}
