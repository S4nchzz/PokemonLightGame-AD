package org.lightPoke.db.dto;

import org.lightPoke.db.dao.interfaces.JoinTournamentRequestDAO_IFACE;

public class JoinTournamentRequestDTO {
    private int id;
    private final TrainerDTO trainer;
    private final TournamentDTO tournament;

    public JoinTournamentRequestDTO(int id, TrainerDTO trainer, TournamentDTO tournament) {
        this.id = id;
        this.trainer = trainer;
        this.tournament = tournament;
    }

    public JoinTournamentRequestDTO(TrainerDTO trainer, TournamentDTO tournament) {
        this.trainer = trainer;
        this.tournament = tournament;
    }

    public int getId() {
        return id;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public TournamentDTO getTournament() {
        return tournament;
    }
}
