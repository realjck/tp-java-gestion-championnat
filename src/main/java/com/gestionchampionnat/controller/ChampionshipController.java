package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.Championship;
import com.gestionchampionnat.repository.ChampionshipRepository;
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

    @Autowired
    public ChampionshipController(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
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
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Le championnat n'existe pas"));
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
     * @param updatedChampionship Championnat mis à jour
     * @return Championnat
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Championship> updateChampionship(@PathVariable Long id, @Valid @RequestBody Championship updatedChampionship) {
        Optional<Championship> optionalChampionship = championshipRepository.findById(id);
        if (optionalChampionship.isPresent()) {
            Championship championship = optionalChampionship.get();
            // Mise à jour des données
            if (updatedChampionship.getName() != null){
                championship.setName(updatedChampionship.getName());
            }
            if (updatedChampionship.getStartDate() != null){
                championship.setStartDate(updatedChampionship.getStartDate());
            }
            if (updatedChampionship.getEndDate() != null){
                championship.setEndDate(updatedChampionship.getEndDate());
            }
            if (updatedChampionship.getWonPoint() != 0){
                championship.setWonPoint(updatedChampionship.getWonPoint());
            }
            if (updatedChampionship.getDrawPoint() != 0){
                championship.setDrawPoint(updatedChampionship.getDrawPoint());
            }
            if (updatedChampionship.getLostPoint() != 0){
                championship.setLostPoint(updatedChampionship.getLostPoint());
            }
            // Sauvegarde
            championshipRepository.save(championship);
            return new ResponseEntity<>(championship, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
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
