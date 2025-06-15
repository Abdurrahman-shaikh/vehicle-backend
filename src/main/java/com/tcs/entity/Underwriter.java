package com.tcs.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an Underwriter in the insurance system.
 * Each Underwriter has a corresponding login and can manage multiple vehicles.
 */
@Entity
@Table(name = "underwriter")
public class Underwriter {

    /**
     * Primary key for the Underwriter table. Auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name of the underwriter. Acts as a username.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Date of birth of the underwriter.
     */
    @Column(nullable = false)
    private LocalDate dob;

    /**
     * Date on which the underwriter joined the organization.
     */
    @Column(name = "date_of_joining", nullable = false)
    private LocalDate dateOfJoining;

    /**
     * One-to-one relationship with the Login entity.
     * Each underwriter must have a corresponding login credential.
     */
    @OneToOne
    @JoinColumn(name = "login_id", referencedColumnName = "id", unique = true)
    private Login login;

    /**
     * One-to-many relationship with vehicles.
     * Each underwriter can manage multiple vehicles.
     */
    @OneToMany(mappedBy = "underwriter", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vehicle> vehicles = new ArrayList<>();

    /** Default constructor for JPA. */
    public Underwriter() {}

    /**
     * Constructor to initialize underwriter fields.
     *
     * @param name           the underwriter's name
     * @param dob            date of birth
     * @param dateOfJoining  joining date
     * @param login          login object linked to underwriter
     */
    public Underwriter(String name, LocalDate dob, LocalDate dateOfJoining, Login login) {
        this.name = name;
        this.dob = dob;
        this.dateOfJoining = dateOfJoining;
        this.login = login;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
