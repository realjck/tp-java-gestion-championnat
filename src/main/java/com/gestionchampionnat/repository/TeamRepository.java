package com.gestionchampionnat.repository;

import com.gestionchampionnat.model.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    @Override
    List<Team> findAll();

    List<Team> findTeamsByChampionshipsId(Long championshipId);
}
