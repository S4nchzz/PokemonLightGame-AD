package org.lightPoke.db.dto;

import org.lightPoke.db.dao.interfaces.JoinTournamentRequestDAO_IFACE;

public class JoinTournamentRequestDTO {
    private int id;
    private final int trainer_id;
    private final int tournament_id;

    public JoinTournamentRequestDTO(int id, int trainer_id, int tournament_id) {
        this.id = id;
        this.trainer_id = trainer_id;
        this.tournament_id = tournament_id;
    }

    public JoinTournamentRequestDTO(int trainer_id, int tournament_id) {
        this.trainer_id = trainer_id;
        this.tournament_id = tournament_id;
    }

    public int getId() {
        return id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public int getTournament_id() {
        return tournament_id;
    }
}
