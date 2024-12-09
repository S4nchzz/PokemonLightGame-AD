package org.lightPoke.db.dto;

public class CombatDTO {
    private final int id;
    private final String date;
    private final TournamentDTO tournamentDto;
    private final TrainerDTO trainer_1;
    private final TrainerDTO trainer_2;
    private final TrainerDTO c_winner;

    public CombatDTO(int id, String date, TournamentDTO tournamentDto, TrainerDTO trainer1, TrainerDTO trainer2, TrainerDTO cWinner) {
        this.id = id;
        this.date = date;
        this.tournamentDto = tournamentDto;
        this.trainer_1 = trainer1;
        this.trainer_2 = trainer2;
        this.c_winner = cWinner;
    }

    public int getId() {
        return this.id;
    }

    public String getDate() {
        return date;
    }

    public TournamentDTO getTournament() { return this.tournamentDto; }

    public TrainerDTO getTrainer_1() {
        return trainer_1;
    }

    public TrainerDTO getTrainer_2() {
        return trainer_2;
    }

    public TrainerDTO getC_winner() {
        return c_winner;
    }
}
