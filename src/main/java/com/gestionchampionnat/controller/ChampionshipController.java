package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.Championship;
import com.gestionchampionnat.repository.ChampionshipRepository;
import com.gestionchampionnat.repository.DayRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/championship")
public class ChampionshipController {

    private final ChampionshipRepository championshipRepository;
    private final DayRepository dayRepository;

    @Autowired
    public ChampionshipController(ChampionshipRepository championshipRepository, DayRepository dayRepository) {
        this.championshipRepository = championshipRepository;
        this.dayRepository = dayRepository;
    }

    /**
     * Récupérer la liste des championnats
     * @return Liste des championnats
     */
    @GetMapping("/")
    public ResponseEntity<List<Championship>> getAllChampionships() {
        List<Championship> championships = new ArrayList<>(championshipRepository.findAll());
        if (championships.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(championships, HttpStatus.OK);
    }

    /**
     * Récupérer un championnat suivant son id
     * @param id Id du championnat
     * @return Championnat
     */
    @GetMapping("/{id}")
    public ResponseEntity<Championship> getChampionshipById(@PathVariable Long id) {
        Championship championship = championshipRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(championship, HttpStatus.OK);
    }

    /**
     * Créer un championnat
     * @param championship Championnat
     * @return Championnat
     */
    @PostMapping(value="/")
    public ResponseEntity<Championship> saveChampionship(@Valid @RequestBody Championship championship) {
        championshipRepository.save(championship);
        return new ResponseEntity<>(championship, HttpStatus.CREATED);
    }

    /**
     * Mettre à jour un championnat
     * @param id Id du championnat
     * @param championship Nouveau Championnat
     * @return Championnat
     */
    @PutMapping("/{id}")
    public ResponseEntity<Championship> updateChampionship(@PathVariable Long id, @Valid @RequestBody Championship championship) {
        Championship existingChampionship = championshipRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingChampionship.setName(championship.getName());
        existingChampionship.setDrawPoint(championship.getDrawPoint());
        existingChampionship.setLostPoint(championship.getLostPoint());
        existingChampionship.setWonPoint(championship.getWonPoint());
        existingChampionship.setStartDate(championship.getStartDate());
        existingChampionship.setEndDate(championship.getEndDate());
        Championship updatedChampionship = championshipRepository.save(existingChampionship);
        return new ResponseEntity<>(updatedChampionship, HttpStatus.OK);
    }

    /**
     * Supprimer un championnat
     * @param id Id du championnat
     * @return Néant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChampionship(@PathVariable Long id) {
        Optional<Championship> championshipOptional = championshipRepository.findById(id);
        if(championshipOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        championshipRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
