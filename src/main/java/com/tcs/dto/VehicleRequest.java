package com.tcs.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for vehicle registration and policy details.
 * Used to capture data from client requests for creating or updating vehicle policies.
 */
public class VehicleRequest {

    /** Vehicle registration number (e.g., "UP 50 AB 1234") */
    private String vehicleNo;

    /** Type of the vehicle (e.g., "Two Wheeler", "Four Wheeler") */
    private String vehicleType;

    /** Name of the customer who owns the vehicle */
    private String customerName;

    /** Engine number of the vehicle */
    private String engineNo;

    /** Chassis number of the vehicle */
    private String chasisNo;

    /** Customer's contact phone number */
    private String phoneNo;

    /** Type of the policy (e.g., "Comprehensive", "Third Party") */
    private String policyType;

    /** Start date of the insurance policy */
    private LocalDate fromDate;

    /** End date of the insurance policy */
    private LocalDate toDate;

    /** Premium amount charged for the policy */
    private Double premiumAmount;

    /** Claim status of the policy (e.g., "Claimed", "Not Claimed") */
    private String claimStatus;

    public String getVehicleNo() { return vehicleNo; }

    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public String getVehicleType() { return vehicleType; }

    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getCustomerName() { return customerName; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEngineNo() { return engineNo; }

    public void setEngineNo(String engineNo) { this.engineNo = engineNo; }

    public String getChasisNo() { return chasisNo; }

    public void setChasisNo(String chasisNo) { this.chasisNo = chasisNo; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getPolicyType() { return policyType; }

    public void setPolicyType(String policyType) { this.policyType = policyType; }

    public LocalDate getFromDate() { return fromDate; }

    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }

    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public Double getPremiumAmount() { return premiumAmount; }

    public void setPremiumAmount(Double premiumAmount) { this.premiumAmount = premiumAmount; }

    public String getClaimStatus() { return claimStatus; }

    public void setClaimStatus(String claimStatus) { this.claimStatus = claimStatus; }
}
