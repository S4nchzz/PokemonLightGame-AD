package org.lightPoke.db.dto;

public class CombatDTO {
    private final String date;
    private final TrainerDTO trainer_1;
    private final TrainerDTO trainer_2;
    private final TrainerDTO c_winner;

    public CombatDTO(String date, TrainerDTO trainer1, TrainerDTO trainer2, TrainerDTO cWinner) {
        this.date = date;
        this.trainer_1 = trainer1;
        this.trainer_2 = trainer2;
        this.c_winner = cWinner;
    }

    public String getDate() {
        return date;
    }

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
