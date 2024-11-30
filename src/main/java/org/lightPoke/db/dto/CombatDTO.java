package org.lightPoke.db.dto;

public class CombatDTO {
    private final String date;
    private final int trainer_1;
    private final int trainer_2;
    private final int c_winner;

    public CombatDTO(String date, int trainer1, int trainer2, int cWinner) {
        this.date = date;
        this.trainer_1 = trainer1;
        this.trainer_2 = trainer2;
        this.c_winner = cWinner;
    }

    public String getDate() {
        return date;
    }

    public int getTrainer_1() {
        return trainer_1;
    }

    public int getTrainer_2() {
        return trainer_2;
    }

    public int getC_winner() {
        return c_winner;
    }
}
