package org.lightPoke.db.dto;

public class TrainerOnTournamentDTO {
    private final int tournamentId;
    private final int trainerId;

    public TrainerOnTournamentDTO(int tournamentId, int trainerId) {
        this.tournamentId = tournamentId;
        this.trainerId = trainerId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public int getTrainerId() {
        return trainerId;
    }
}
