package org.lightPoke.db.mysql.services;

import org.lightPoke.db.mysql.entity.Ent_JoinTournamentRequest;
import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.lightPoke.db.mysql.repo.Repo_JoinTournamentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Svice_JoinTournamentRequest {
    @Autowired
    private Repo_JoinTournamentRequest repoJoinTournamentRequest;

    public void addRequestFromTrainer(Ent_JoinTournamentRequest entJoinTournamentRequest) {
        repoJoinTournamentRequest.save(entJoinTournamentRequest);
    }

    public List<Ent_JoinTournamentRequest> getRequestsFromTournament(Ent_Tournament tournament) {
        return repoJoinTournamentRequest.findByTournamentId(tournament.getId());
    }

    public boolean tournamentRequestsIsEmpty(final int t_id) {
        List<Ent_JoinTournamentRequest> requests = repoJoinTournamentRequest.findByTournamentId(t_id);
        return requests != null && requests.isEmpty();
    }

    public boolean trainerHasPendingRequests(int trainer_id) {
        List<Ent_JoinTournamentRequest> requests = repoJoinTournamentRequest.findByTrainerId(trainer_id);
        return requests != null && !requests.isEmpty();
    }

    public void deleteRequest(int id) {
        repoJoinTournamentRequest.deleteById(id);
    }

    public void deleteAllRequestsFromTournament(int tId) {
        for (Ent_JoinTournamentRequest req : repoJoinTournamentRequest.findByTournamentId(tId)) {
            repoJoinTournamentRequest.deleteById(req.getId());
        }
    }
}
