package com.tcs.service;

import com.tcs.dto.VehicleRequest;
import com.tcs.entity.Underwriter;
import com.tcs.entity.Vehicle;
import com.tcs.exception.DatabaseException;
import com.tcs.exception.PolicyNotFoundException;
import com.tcs.exception.RenewalException;
import com.tcs.exception.UpdateException;
import com.tcs.repository.UnderwriterRepository;
import com.tcs.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private UnderwriterRepository underwriterRepo;

    public void registerVehicle(VehicleRequest req, String username) {
        Underwriter underwriter = underwriterRepo.findByName(username);

        Vehicle v = new Vehicle();
        v.setVehicleNo(req.getVehicleNo());
        v.setVehicleType(req.getVehicleType());
        v.setCustomerName(req.getCustomerName());
        v.setEngineNo(req.getEngineNo());
        v.setChasisNo(req.getChasisNo());
        v.setPhoneNo(req.getPhoneNo());
        v.setPolicyType(req.getPolicyType());
        v.setFromDate(req.getFromDate());
        v.setToDate(req.getToDate());
        v.setPremiumAmount(req.getPremiumAmount());
        v.setClaimStatus(req.getClaimStatus());
        v.setUnderwriter(underwriter);

        vehicleRepo.save(v);
    }

    public List<Vehicle> getVehiclesByUnderwriter(String username) {
        return vehicleRepo.findByUnderwriterLoginUsername(username);
    }

    public List<Vehicle> getAllPolicies() {
        try {
            return vehicleRepo.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to fetch policies");
        }
    }

    public Vehicle getPolicyById(Long policyId) {
        return vehicleRepo.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found"));
    }

//    public Vehicle updatePolicyType(Long policyId, String newPolicyType) {
//        Vehicle policy = getPolicyById(policyId);
//
//        if (!policy.getPolicyType().equalsIgnoreCase("Full Insurance")) {
//            throw new UpdateException("Only Full Insurance policies can be updated");
//        }
//
//        policy.setPolicyType(newPolicyType);
//        try {
//            return vehicleRepo.save(policy);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Failed to update policy type");
//        }
//    }

    public Vehicle updatePolicyType(Long policyId, String newPolicyType, String username) {
        Vehicle policy = vehicleRepo.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found"));

        // Check ownership
        if (!policy.getUnderwriter().getLogin().getUsername().equals(username)) {
            throw new UpdateException("You are not authorized to update this policy");
        }

        if (!policy.getPolicyType().equals("Full Insurance")) {
            throw new UpdateException("Only Full Insurance policies can be updated");
        }

        policy.setPolicyType(newPolicyType);
        return vehicleRepo.save(policy);
    }

    public Vehicle renewPolicy(Long policyId, String claimStatus, String username) {
        Vehicle existingPolicy = getPolicyById(policyId);
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = existingPolicy.getToDate();

        if (today.isBefore(expiryDate.minusMonths(1))) {
            throw new RenewalException("Policy can only be renewed 1 month before expiry or after expiry date");
        }

        Vehicle newPolicy = new Vehicle();
        newPolicy.setVehicleNo(existingPolicy.getVehicleNo());
        newPolicy.setVehicleType(existingPolicy.getVehicleType());
        newPolicy.setCustomerName(existingPolicy.getCustomerName());
        newPolicy.setEngineNo(existingPolicy.getEngineNo());
        newPolicy.setChasisNo(existingPolicy.getChasisNo());
        newPolicy.setPhoneNo(existingPolicy.getPhoneNo());
        newPolicy.setPolicyType(existingPolicy.getPolicyType());
        newPolicy.setClaimStatus(claimStatus);

        LocalDate newFromDate = today.isAfter(expiryDate) ? today : expiryDate.plusDays(1);
        newPolicy.setFromDate(newFromDate);
        newPolicy.setToDate(newFromDate.plusYears(1));

        double basePremium = 1000.0;
        newPolicy.setPremiumAmount(
                existingPolicy.getVehicleType().equalsIgnoreCase("2-wheeler") ?
                        basePremium * 0.95 :
                        basePremium * 0.85
        );

        Underwriter underwriter = underwriterRepo.findByName(username);
        newPolicy.setUnderwriter(underwriter);

        return vehicleRepo.save(newPolicy);
    }
}
