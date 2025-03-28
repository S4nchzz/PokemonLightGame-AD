package org.lightPoke.db.mysql.repo;

import org.lightPoke.db.mysql.entity.Ent_JoinTournamentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_JoinTournamentRequest extends JpaRepository<Ent_JoinTournamentRequest, Integer> {
    @Query("select j from pkm_join_tournament_req j where j.entTournament.id = ?1")
    List<Ent_JoinTournamentRequest> findByTournamentId(int id);

    @Query("select j from pkm_join_tournament_req j where j.entTrainer.id = ?1")
    List<Ent_JoinTournamentRequest> findByTrainerId(int trainerId);
}
