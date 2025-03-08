package org.lightPoke.db.mysql.repo;

import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_Tournament extends JpaRepository<Ent_Tournament, Integer> {
    @Query("select t from pkm_tournament t where name = ?1 AND region = ?2")
    List<Ent_Tournament> findByNameAndRegion(String name, char region);

    @Query("select t from pkm_tournament t where t.t_winner.id = ?1")
    Ent_Tournament findTournamentByWinner(int wId);
}
