package com.gestionchampionnat.controller;

import com.gestionchampionnat.model.User;
import com.gestionchampionnat.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupérer la liste des utilisateurs
     * -----------------------------------
     * @return List<User>
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = new ArrayList<>(userRepository.findAll());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Récupérer un utilisateur suivant son id
     * ---------------------------------------
     * @param user Long
     * @return User
     */
    @GetMapping(value="/{user}")
    public User getUserById(@PathVariable(name="user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Utilisateur introuvable"
            );
        }
        return user;
    }

    /**
     * Récupérer un utilisateur suivant son adresse mail et son mot de passe
     * ---------------------------------------------------------------------
     * @param email String
     * @param password String
     * @return User
     */
    @GetMapping(value = "/getUserByEmailAndPassword")
    public ResponseEntity<User> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .toList();
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
        return new ResponseEntity<>(users.get(0), HttpStatus.OK);
    }

    /**
     * Créer un utilisateur
     * --------------------
     * @param user User
     * @return ResponseEntity<User>
     */
    @PostMapping("/")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        // date de création à date du jour
        user.setCreationDate(LocalDate.now());
        // hash du password
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        // emails uniques entre chaque utilisateur
        try {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Cette adresse e-mail est déjà utilisée.");
        }
    }

    /**
     * Mettre à jour un utilisateur
     * ----------------------------
     * @param id Long
     * @param user User
     * @return ResponseEntity<User>
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
        User existingUser = userOptional.get();
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        User updatedUser = userRepository.save(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Supprimer un utilisateur
     * ------------------------
     * @param user User
     * @return ResponseEntity<Void>
     */
    @DeleteMapping(value = "/{user}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name="user", required = false) User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
