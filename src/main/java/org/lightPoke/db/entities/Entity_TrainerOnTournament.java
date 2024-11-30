package org.lightPoke.db.entities;

public record Entity_TrainerOnTournament(int id, int id_tournament, int id_trainer){
    public Entity_TrainerOnTournament(final int id_tournament, final int id_Trainer) {
        this(-1, id_tournament, id_Trainer);
    }
}