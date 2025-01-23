package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.JoinTournamentRequestDAO_IMPLE;
import org.lightPoke.db.entity.Ent_JoinTournamentRequest;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.entity.Entity_JoinTournamentRequest;
import org.lightPoke.db.repo.Repo_JoinTournamentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Svice_JoinTournamentRequest {
    private static Svice_JoinTournamentRequest instance;

    @Autowired
    private final Repo_JoinTournamentRequest repoJoinTournamentRequest;

    private Svice_JoinTournamentRequest(Repo_JoinTournamentRequest repoJoinTournamentRequest) {
        this.repoJoinTournamentRequest = repoJoinTournamentRequest;
    }

    public void addRequestFromTrainer(int trainer_id, int tournament_id) {
        joinTournamentRequestDAO.addRequestFromUser(trainer_id, tournament_id);
    }

    public List<Ent_JoinTournamentRequest> getRequestsFromTournament(Ent_Tournament tournament) {
        List<Entity_JoinTournamentRequest> entityList = joinTournamentRequestDAO.getRequestsByTournamentId(tournament.getId());

        List<Ent_JoinTournamentRequest> dtoList = new ArrayList<>();

        for (Entity_JoinTournamentRequest e : entityList) {
            dtoList.add(entityToDto(e));
        }

        return dtoList;
    }

    public boolean tournamentRequestsIsEmpty(final int t_id) {
        List<Entity_JoinTournamentRequest> requests = joinTournamentRequestDAO.getRequestsByTournamentId(t_id);
        return requests == null || requests.isEmpty();
    }

    public boolean trainerHasPendingRequests(int trainer_id) {
        return joinTournamentRequestDAO.getRequestsByTrainerId(trainer_id) != null && !joinTournamentRequestDAO.getRequestsByTrainerId(trainer_id).isEmpty();
    }

    public void deleteRequest(int trainer_id, int tournament_id) {
        joinTournamentRequestDAO.deleteRequest(trainer_id, tournament_id);
    }
}
