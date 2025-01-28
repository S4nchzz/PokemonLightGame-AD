package org.lightPoke.db.repo;

import org.lightPoke.db.entity.Ent_JoinTournamentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repo_JoinTournamentRequest extends JpaRepository<Ent_JoinTournamentRequest, Integer> {
    @Query()
    List<Ent_JoinTournamentRequest> findByTournamentId(int id);

    @Query()
    Repo_JoinTournamentRequest findByTrainerId(int trainerId);

    void addRequestFromUser(int trainerId, int tournamentId);
}
