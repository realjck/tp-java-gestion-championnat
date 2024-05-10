package com.gestionchampionnat.repository;

import com.gestionchampionnat.model.Championship;
import com.gestionchampionnat.model.Day;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DayRepository extends CrudRepository<Day, Long> {
    @Override
    List<Day> findAll();

    List<Day> findByChampionship(Championship championship);
}
