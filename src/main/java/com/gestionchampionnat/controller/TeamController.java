package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.Championship;
import com.gestionchampionnat.model.Team;
import com.gestionchampionnat.repository.ChampionshipRepository;
import com.gestionchampionnat.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/team")
public class TeamController {

    private final TeamRepository teamRepository;
    private final ChampionshipRepository championshipRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository, ChampionshipRepository championshipRepository) {
        this.teamRepository = teamRepository;
        this.championshipRepository = championshipRepository;
    }

    /**
     * Récupérer la liste des équipes
     * ------------------------------
     * @return ResponseEntity<List<Team>>
     */
    @GetMapping("/")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = new ArrayList<>(teamRepository.findAll());
        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    /**
     * Récupérer la liste des équipes suivant l’id d’un championnat
     * ------------------------------------------------------------
     * @param championshipId Long
     * @return ResponseEntity<List<Team>>
     */
    @GetMapping("/championship/{championshipId}")
    public ResponseEntity<List<Team>> getAllTeamsByChampionshipId(@PathVariable Long championshipId) {
        if (!championshipRepository.existsById(championshipId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Team> teams = teamRepository.findTeamsByChampionshipsId(championshipId);
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    /**
     * Récupérer une équipe suivant son id
     * -----------------------------------
     * @param id Long
     * @return Team
     */
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    /**
     * Créer une équipe
     * ----------------
     * @param team Team
     * @return ResponseEntity<Team>
     */
    @PostMapping("/")
    public ResponseEntity<Team> saveTeam(@Valid @RequestBody Team team) {
        team.setCreationDate(LocalDate.now());
        Team savedTeam = teamRepository.save(team);
        return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
    }

    /**
     * Ajouter une équipe à un championnat
     * -----------------------------------
     * @param teamId Long
     * @param championshipId Long
     * @return ResponseEntity<String>
     */
    @PostMapping("/add-to-championship/{teamId}/{championshipId}")
    public ResponseEntity<String> addTeamToChampionship(@PathVariable Long teamId, @PathVariable Long championshipId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (optionalTeam.isEmpty()) {
            return new ResponseEntity<>("Équipe non trouvée", HttpStatus.NOT_FOUND);
        }
        Optional<Championship> optionalChampionship = championshipRepository.findById(championshipId);
        if (optionalChampionship.isEmpty()) {
            return new ResponseEntity<>("Championnat non trouvé", HttpStatus.NOT_FOUND);
        }
        Team team = optionalTeam.get();
        Championship championship = optionalChampionship.get();
        team.addChampionship(championship);
        teamRepository.save(team);
        return new ResponseEntity<>("Équipe ajoutée au championnat avec succès", HttpStatus.OK);
    }

    /**
     * Mettre à jour une équipe
     * ------------------------
     * @param id Long
     * @param updatedTeam Team
     * @return ResponseEntity<Team>
     */
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @Valid @RequestBody Team updatedTeam) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Team team = optionalTeam.get();
        team.setName(updatedTeam.getName());
        Team savedTeam = teamRepository.save(team);
        return new ResponseEntity<>(savedTeam, HttpStatus.OK);
    }

    /**
     * Supprimer une équipe
     * ---------------------
     * @param id Long
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isEmpty()) {
            return new ResponseEntity<>("Équipe non trouvée", HttpStatus.NOT_FOUND);
        }

        teamRepository.deleteById(id);
        return new ResponseEntity<>("Équipe supprimée avec succès", HttpStatus.OK);
    }


}
