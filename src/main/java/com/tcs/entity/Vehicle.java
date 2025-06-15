package com.tcs.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity representing a vehicle insured under a policy.
 * Each vehicle is linked to an underwriter and contains insurance-related details.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {

    /**
     * Unique policy ID (primary key) for the vehicle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    /**
     * Vehicle number (license plate), max 10 characters.
     */
    @Column(length = 10)
    private String vehicleNo;

    /**
     * Type of vehicle (e.g., car, bike, truck).
     */
    private String vehicleType;

    /**
     * Name of the customer who owns the vehicle.
     */
    @Column(length = 50)
    private String customerName;

    /**
     * Engine number of the vehicle.
     */
    private String engineNo;

    /**
     * Chassis number of the vehicle.
     */
    private String chasisNo;

    /**
     * Phone number of the customer (max 10 characters).
     */
    @Column(length = 10)
    private String phoneNo;

    /**
     * Policy type (e.g., comprehensive, third-party).
     */
    private String policyType;

    /**
     * Start date of the policy coverage.
     */
    private LocalDate fromDate;

    /**
     * End date of the policy coverage.
     */
    private LocalDate toDate;

    /**
     * Premium amount paid for the policy.
     */
    private Double premiumAmount;

    /**
     * Claim status (e.g., "Pending", "Approved", "Rejected").
     */
    private String claimStatus;

    /**
     * Many-to-one relationship with Underwriter.
     * Each vehicle must be linked to one underwriter.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underwriter_id", nullable = false)
    @JsonBackReference
    private Underwriter underwriter;

    /** Default constructor for JPA. */
    public Vehicle() {}

    // Getters and Setters

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChasisNo() {
        return chasisNo;
    }

    public void setChasisNo(String chasisNo) {
        this.chasisNo = chasisNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Underwriter getUnderwriter() {
        return underwriter;
    }

    public void setUnderwriter(Underwriter underwriter) {
        this.underwriter = underwriter;
    }
}
