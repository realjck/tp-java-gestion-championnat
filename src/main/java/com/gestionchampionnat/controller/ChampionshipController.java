package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.Championship;
import com.gestionchampionnat.repository.ChampionshipRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/championship")
public class ChampionshipController {

    private final ChampionshipRepository championshipRepository;

    public ChampionshipController(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    @GetMapping("/")
    public List<Championship> all() {
        return championshipRepository.findAll();
    }

    @PostMapping(value="/")
    public ResponseEntity<Championship> saveChampionship(@Valid @RequestBody Championship championship) {
        championshipRepository.save(championship);
        return new ResponseEntity<>(championship, HttpStatus.CREATED);
    }

}
