package com.gestionchampionnat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team1_point")
    @NotNull(message = "Le champ Team1 point ne peut pas être null")
    private int team1Point;

    @Column(name = "team2_point")
    @NotNull(message = "Le champ Team2 point ne peut pas être null")
    private int team2Point;

    @ManyToOne
    @JoinColumn(name = "id_team1")
    @NotNull(message = "Le champ id_team1 ne peut pas être null")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "id_team2")
    @NotNull(message = "Le champ id_team2 ne peut pas être null")
    private Team team2;

    @ManyToOne
    @JoinColumn(name = "id_day")
    @NotNull(message = "Le champ id_day ne peut pas être null")
    private Day day;

    public Game(){}

    /**
     * Résultats d'un jeu
     * @param team1Point Points de l'équipe 1
     * @param team2Point Points de l'équipe 2
     * @param team1 Équipe 1
     * @param team2 Équipe 2
     * @param day Journée
     */
    public Game(int team1Point, int team2Point, Team team1, Team team2, Day day) {
        this.team1Point = team1Point;
        this.team2Point = team2Point;
        this.team1 = team1;
        this.team2 = team2;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "Le champ Team1 point ne peut pas être null")
    public int getTeam1Point() {
        return team1Point;
    }

    public void setTeam1Point(@NotNull(message = "Le champ Team1 point ne peut pas être null") int team1Point) {
        this.team1Point = team1Point;
    }

    @NotNull(message = "Le champ Team2 point ne peut pas être null")
    public int getTeam2Point() {
        return team2Point;
    }

    public void setTeam2Point(@NotNull(message = "Le champ Team2 point ne peut pas être null") int team2Point) {
        this.team2Point = team2Point;
    }

    public @NotNull(message = "Le champ id_team1 ne peut pas être null") Team getTeam1() {
        return team1;
    }

    public void setTeam1(@NotNull(message = "Le champ id_team1 ne peut pas être null") Team team1) {
        this.team1 = team1;
    }

    public @NotNull(message = "Le champ id_team2 ne peut pas être null") Team getTeam2() {
        return team2;
    }

    public void setTeam2(@NotNull(message = "Le champ id_team2 ne peut pas être null") Team team2) {
        this.team2 = team2;
    }

    public @NotNull(message = "Le champ id_day ne peut pas être null") Day getDay() {
        return day;
    }

    public void setDay(@NotNull(message = "Le champ id_day ne peut pas être null") Day day) {
        this.day = day;
    }
}
