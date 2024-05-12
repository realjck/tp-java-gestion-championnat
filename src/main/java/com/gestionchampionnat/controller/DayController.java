package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.Championship;
import com.gestionchampionnat.model.Day;
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
@RequestMapping(value = "/api/day")
public class DayController {

    private final DayRepository dayRepository;

    private final ChampionshipRepository championshipRepository;

    @Autowired
    public DayController(DayRepository dayRepository, ChampionshipRepository championshipRepository){
        this.dayRepository = dayRepository;
        this.championshipRepository = championshipRepository;
    }

    /**
     * Récupérer la liste des journées
     * @return Liste des journées
     */
    @GetMapping("/")
    public ResponseEntity<List<Day>> getAllDays() {
        List<Day> days = new ArrayList<>(dayRepository.findAll());
        if (days.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    /**
     * Récupérer la liste des journées suivant l’id d’un championnat
     * @param id Id du championnat
     * @return Liste des journées
     */
    @GetMapping("/championship/{id}")
    public ResponseEntity<List<Day>> getAllDaysByChampionshipId(@PathVariable Long id) {
        Optional<Championship> championshipOptional = championshipRepository.findById(id);
        if (championshipOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Championship championship = championshipOptional.get();
        List<Day> days = dayRepository.findByChampionship(championship);
        return new ResponseEntity<>(days, HttpStatus.OK);
    }

    /**
     * Récupérer une journée suivant son id
     * @param id Id de la journée
     * @return Journée
     */
    @GetMapping("/{id}")
    public ResponseEntity<Day> getDayById(@PathVariable Long id) {
        Optional<Day> dayOptionnal = dayRepository.findById(id);
        if (dayOptionnal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Day day = dayOptionnal.get();
        return new ResponseEntity<>(day, HttpStatus.OK);
   }

    /**
     * Créer une journée pour un championnat
     * {
     *     "number": "Journée#01",
     *     "championship": {
     *         "id": 1
     *     }
     * }
      * @param day Journée
     * @return Journée
     */
   @PostMapping("/")
    public ResponseEntity<Day> saveDay(@Valid @RequestBody Day day){
        Championship championship = championshipRepository.findById(day.getChampionship().getId())
                .orElseThrow(() -> new RuntimeException("Championnat non trouvé"));
        day.setChampionship(championship);
        Day createdDay = dayRepository.save(day);
        return new ResponseEntity<>(createdDay, HttpStatus.CREATED);
    }

    /**
     * Mettre à jour une journée
     * @param id Id de la journée
     * @param day Journée
     * @return Journée
     */
    @PutMapping("/{id}")
    public ResponseEntity<Day> updateDay(@PathVariable Long id, @Valid @RequestBody Day day) {
        Optional<Day> dayOptional = dayRepository.findById(id);
        if (dayOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Day existingDay = dayOptional.get();
        existingDay.setNumber(day.getNumber());
        Championship championship = championshipRepository.findById(day.getChampionship().getId())
                .orElseThrow(() -> new RuntimeException("Championnat non trouvé"));
        existingDay.setChampionship(championship);
        Day updatedDay = dayRepository.save(existingDay);
        return new ResponseEntity<>(updatedDay, HttpStatus.OK);
    }

    /**
     * Supprimer une journée
     * @param id Id de la journée
     * @return Néant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDay(@PathVariable Long id) {
        Optional<Day> dayOptional = dayRepository.findById(id);
        if (dayOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        dayRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
