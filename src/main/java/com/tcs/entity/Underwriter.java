package com.tcs.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "underwriter", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Underwriter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(name = "date_of_joining", nullable = false)
    private LocalDate dateOfJoining;

    public Underwriter() {
    }

    public void setId(Long id) {
        this.id = id;
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

    public Underwriter(String name, String password, LocalDate dob, LocalDate dateOfJoining) {
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.dateOfJoining = dateOfJoining;
    }

    @Override
    public String toString() {
        return "UnderwriterController{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Underwriter getLogin() {
        return null;
    }
}
