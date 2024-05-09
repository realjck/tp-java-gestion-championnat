package com.gestionchampionnat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "Le champ ne peut pas être null")
    @NotBlank(message = "Le champ ne peut pas être vide")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Le champ ne peut pas être null")
    @NotBlank(message = "Le champ ne peut pas être vide")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotNull(message = "Le champ ne peut pas être null")
    @NotBlank(message = "Le champ ne peut pas être vide")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Le champ ne peut pas être null")
    @NotBlank(message = "Le champ ne peut pas être vide")
    private String password;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.creationDate = LocalDate.now();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
