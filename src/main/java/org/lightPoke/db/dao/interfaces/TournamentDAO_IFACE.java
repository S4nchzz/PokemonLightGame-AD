package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_Tournament;

import java.util.List;

public interface TournamentDAO_IFACE {
    Entity_Tournament createTournament(Entity_Tournament entity);
    void removeTournament(final int tournamentId);
    Entity_Tournament getTournamentById(int id);
    List<Entity_Tournament> getTournamentsFromUserById(int id);
    List<Entity_Tournament> getAllTournaments();
    Entity_Tournament getTournamentByNameAndRegion(final Entity_Tournament entity);
}