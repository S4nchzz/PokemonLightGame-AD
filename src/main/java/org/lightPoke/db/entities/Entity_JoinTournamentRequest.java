package org.lightPoke.db.entities;

public record Entity_JoinTournamentRequest(int id, int trainer_id, int tournament_id) {
    public Entity_JoinTournamentRequest(int trainer_id, int tournament_id) {
        this(-1, trainer_id, tournament_id);
    }
}
