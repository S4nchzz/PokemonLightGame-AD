package org.lightPoke.db.entities;

public record Entity_AT_InTournament(int id, String username, int tournament_id) {
    public Entity_AT_InTournament(String username, int tournament_id) {
        this(-1, username, tournament_id);
    }
}
