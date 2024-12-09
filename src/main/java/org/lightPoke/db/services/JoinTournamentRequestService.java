package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.JoinTournamentRequestDAO_IMPLE;
import org.lightPoke.db.dto.JoinTournamentRequestDTO;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_JoinTournamentRequest;

import java.util.ArrayList;
import java.util.List;

public class JoinTournamentRequestService {
    private static JoinTournamentRequestService instance;
    private JoinTournamentRequestDAO_IMPLE joinTournamentRequestDAO;

    private JoinTournamentRequestService() {
        joinTournamentRequestDAO = JoinTournamentRequestDAO_IMPLE.getInstance();
    }

    private JoinTournamentRequestDTO entityToDto(Entity_JoinTournamentRequest entity) {
        TournamentService tournamentService = TournamentService.getInstance();
        TournamentDTO tournament = tournamentService.getTournamentById(entity.tournament_id());

        TrainerService trainerService = TrainerService.getInstance();
        TrainerDTO trainer = trainerService.getTrainerById(entity.trainer_id());

        return new JoinTournamentRequestDTO(entity.id(), trainer, tournament);
    }

    public static JoinTournamentRequestService getInstance() {
        if (instance == null) {
            instance = new JoinTournamentRequestService();
        }

        return instance;
    }


    public void addRequestFromTrainer(int trainer_id, int tournament_id) {
        joinTournamentRequestDAO.addRequestFromUser(trainer_id, tournament_id);
    }

    public List<JoinTournamentRequestDTO> getRequestsFromTournament(TournamentDTO tournament) {
        List<Entity_JoinTournamentRequest> entityList = joinTournamentRequestDAO.getRequestsByTournamentId(tournament.getId());

        List<JoinTournamentRequestDTO> dtoList = new ArrayList<>();

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
