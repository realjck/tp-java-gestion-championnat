package com.gestionchampionnat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Le champ ne peut pas être null")
    @NotBlank(message = "Le champ ne peut pas être vide")
    private String name;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
        })
    @JoinTable(name = "team_championship",
    joinColumns = { @JoinColumn(name="team_id") },
    inverseJoinColumns = { @JoinColumn(name="championship_id") })
    private final Set<Championship> championships = new HashSet<>();

    public Team() {}

    public Team(String name, LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public void addChampionship(Championship championship) {
        this.championships.add(championship);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
