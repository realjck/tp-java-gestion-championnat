package com.gestionchampionnat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    @NotNull(message = "Le champ number ne peut pas être null")
    @NotBlank(message = "Le champ number ne peut pas être vide")
    private String number;

    @ManyToOne
    @JoinColumn(name = "id_championship")
    @NotNull(message = "Le champ championship ne peut pas être null")
    private Championship championship;

    public Day() {
    }

    /**
     * Journée de championnat, définie par son number et son championnat de référence
     * @param number String
     * @param championship Championship
     */
    public Day(String number, Championship championship) {
        this.number = number;
        this.championship = championship;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }
}
