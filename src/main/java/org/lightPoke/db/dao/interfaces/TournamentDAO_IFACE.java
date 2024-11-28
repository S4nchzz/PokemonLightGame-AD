package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_Tournament;

public interface TournamentDAO_IFACE {
    void createTournament(Entity_Tournament entity);
    void removeTournament(final int tournamentId);
}
