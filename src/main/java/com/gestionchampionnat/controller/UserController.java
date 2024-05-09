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
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping(value="/{user}")
    public User getOne(@PathVariable(name="user", required = false) User user) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Utilisateur introuvable"
            );
        }
        return user;
    }

    @GetMapping(value = "/getByEmailAndPassword")
    public List<User> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .toList();
    }

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

    @DeleteMapping(value = "/{user}")
    public void deleteUser(@PathVariable(name="user", required = false) User user) {
        userRepository.delete(user);
    }
}
