package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.JoinTournamentRequestDAO_IMPLE;

public class JoinTournamentRequestService {
    private static JoinTournamentRequestService instance;
    private JoinTournamentRequestDAO_IMPLE joinTournamentRequestDAO;

    private JoinTournamentRequestService() {
        joinTournamentRequestDAO = JoinTournamentRequestDAO_IMPLE.getInstance();
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
}
