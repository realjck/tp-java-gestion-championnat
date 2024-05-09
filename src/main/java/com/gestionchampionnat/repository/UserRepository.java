package com.gestionchampionnat.repository;

import com.gestionchampionnat.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    List<User> findByEmailAndPassword(String email, String password);
}
