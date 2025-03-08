package org.lightPoke.db.mongo.collections.models;

import org.springframework.data.mongodb.core.mapping.Field;

public class CombatModel {
    @Field("_id")
    private int id;

    @Field("date")
    private String date;

    @Field("trainer_1")
    private TrainerModel trainer_1;

    @Field("trainer_2")
    private TrainerModel trainer_2;

    @Field("c_winner")
    private TrainerModel c_winner;

    public CombatModel() {}

    public CombatModel(int id, String date, TrainerModel trainer_1, TrainerModel trainer_2, TrainerModel c_winner) {
        this.id = id;
        this.date = date;
        this.trainer_1 = trainer_1;
        this.trainer_2 = trainer_2;
        this.c_winner = c_winner;
    }

    // Getters y setters (los añadirás tú)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TrainerModel getTrainer_1() {
        return trainer_1;
    }

    public void setTrainer_1(TrainerModel trainer_1) {
        this.trainer_1 = trainer_1;
    }

    public TrainerModel getTrainer_2() {
        return trainer_2;
    }

    public void setTrainer_2(TrainerModel trainer_2) {
        this.trainer_2 = trainer_2;
    }

    public TrainerModel getC_winner() {
        return c_winner;
    }

    public void setC_winner(TrainerModel c_winner) {
        this.c_winner = c_winner;
    }
}