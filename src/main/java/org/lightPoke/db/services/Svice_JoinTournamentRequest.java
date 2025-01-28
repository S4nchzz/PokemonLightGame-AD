package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_JoinTournamentRequest;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.repo.Repo_JoinTournamentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Svice_JoinTournamentRequest {
    private static Svice_JoinTournamentRequest instance;

    @Autowired
    private final Repo_JoinTournamentRequest repoJoinTournamentRequest;

    private Svice_JoinTournamentRequest(Repo_JoinTournamentRequest repoJoinTournamentRequest) {
        this.repoJoinTournamentRequest = repoJoinTournamentRequest;
    }

    public void addRequestFromTrainer(Ent_JoinTournamentRequest entJoinTournamentRequest) {
        repoJoinTournamentRequest.save(entJoinTournamentRequest);
    }

    public List<Ent_JoinTournamentRequest> getRequestsFromTournament(Ent_Tournament tournament) {
        return repoJoinTournamentRequest.findByTournamentId(tournament.getId());
    }

    public boolean tournamentRequestsIsEmpty(final int t_id) {
        List<Ent_JoinTournamentRequest> requests = repoJoinTournamentRequest.findByTournamentId(t_id);
        return requests == null;
    }

    public boolean trainerHasPendingRequests(int trainer_id) {
        return repoJoinTournamentRequest.findByTrainerId(trainer_id) == null;
    }

    public void deleteRequest(Ent_JoinTournamentRequest entJoinTournamentRequest) {
        repoJoinTournamentRequest.delete(entJoinTournamentRequest);
    }
}
