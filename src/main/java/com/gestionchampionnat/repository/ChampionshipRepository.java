package com.gestionchampionnat.repository;

import com.gestionchampionnat.model.Championship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChampionshipRepository extends CrudRepository<Championship, Long> {

    @Override
    List<Championship> findAll();

}
