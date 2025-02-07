package org.lightPoke.db.repo;

import org.lightPoke.db.entity.Ent_Combat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_Combat extends JpaRepository<Ent_Combat, Integer> {
    @Query("select c from pkm_combat c where c.trainer_1.id = ?1 OR c.trainer_2.id = ?1")
    List<Ent_Combat> findByTrainerId(int trainer_id);

    @Query("select DISTINCT c.tournament.id from pkm_combat c where c.trainer_1.id = ?1 OR c.trainer_2.id = ?1")
    List<Integer> findDistinctByTrainerId(int trainer_id);

    @Query("select c from pkm_combat c where c.tournament.id = ?1")
    List<Ent_Combat> findByTournamentId(int tId);

    @Query("select c from pkm_combat c where c.c_winner.id = ?1")
    List<Ent_Combat> findCombatsFinishedByTrainerId(int id);

    @Query("select c from pkm_combat c where c.c_winner.id = ?1")
    List<Ent_Combat> findByWinnerId(int trainerId);

    @Query("select c from pkm_combat c where c.tournament.id = ?1")
    List<Ent_Combat> findCombatsByTournamentId(int tournamentId);
}