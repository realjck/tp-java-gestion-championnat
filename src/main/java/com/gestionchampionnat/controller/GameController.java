package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.Day;
import com.gestionchampionnat.model.Game;
import com.gestionchampionnat.model.Team;
import com.gestionchampionnat.repository.DayRepository;
import com.gestionchampionnat.repository.GameRepository;
import com.gestionchampionnat.repository.TeamRepository;
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
@RequestMapping(value = "/api/game")
public class GameController {

    private final GameRepository gameRepository;
    private final DayRepository dayRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public GameController(GameRepository gameRepository, DayRepository dayRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.dayRepository = dayRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Récupérer la liste des résultats
     * @return Liste des résultats
     */
    @GetMapping("/")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = new ArrayList<>(gameRepository.findAll());
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    /**
     * Récupérer la liste des résultats suivant l’id d’un championnat
     * @param championshipId Id du championnat
     * @return Liste de résultats
     */
    @GetMapping("/championship/{championshipId}")
    public ResponseEntity<List<Game>> getAllGamesByChampionshipId(@PathVariable Long championshipId) {
        List<Game> games = gameRepository.findByDayChampionshipId(championshipId);
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    /**
     * Récupérer la liste des résultats suivant l’id d’une journée
     * @param dayId Id de la journée
     * @return Liste des résultats
     */
    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<Game>> getAllGamesByDayId(@PathVariable Long dayId) {
        List<Game> games = gameRepository.findByDayId(dayId);
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    /**
     * Récupérer un résultat suivant son id
     * @param id Id du résultat
     * @return Résultat
     */
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Game game = gameOptional.get();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    /**
     * Créer un résultat pour une journée
     * {
     *     "team1Point": 3,
     *     "team2Point": 1,
     *     "team1": {
     *         "id":1
     *     },
     *     "team2": {
     *         "id":2
     *     },
     *     "day" : {
     *         "id":1
     *     }
     * }
     * @param game Résultat
     * @return Résultat
     */
    @PostMapping("/")
    public ResponseEntity<Game> saveGame(@Valid @RequestBody Game game) {
        // Vérification de la Team1
        Team team1 = game.getTeam1();
        Team existingTeam1 = teamRepository.findById(team1.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team1 non trouvée dans la base de données"));
        game.setTeam1(existingTeam1);

        // Vérification de la Team2
        Team team2 = game.getTeam2();
        Team existingTeam2 = teamRepository.findById(team2.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team2 non trouvée dans la base de données"));
        game.setTeam2(existingTeam2);

        // Vérification de la journée
        Day day = game.getDay();
        Day existingDay = dayRepository.findById(day.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Journée non trouvée dans la base de données"));
        game.setDay(existingDay);

        Game savedGame = gameRepository.save(game);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    /**
     * Mettre à jour un résultat
     * @param id Id du résultat
     * @param updatedGame Nouveau résultat
     * @return Résultat
     */
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @Valid @RequestBody Game updatedGame) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Game existingGame = gameOptional.get();
        existingGame.setTeam1(teamRepository.findById(updatedGame.getTeam1().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team1 non trouvée dans la base de données")));
        existingGame.setTeam2(teamRepository.findById(updatedGame.getTeam2().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team2 non trouvée dans la base de données")));
        existingGame.setTeam1Point(updatedGame.getTeam1Point());
        existingGame.setTeam2Point(updatedGame.getTeam2Point());
        existingGame.setDay(dayRepository.findById(updatedGame.getDay().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Journée non trouvée dans la base de données")));
        Game game = gameRepository.save(existingGame);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    /**
     * Supprimer un résultat
     * @param id Id du résultat
     * @return Néant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
