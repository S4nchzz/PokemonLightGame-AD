package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_JoinTournamentRequest;

import java.util.List;

public interface JoinTournamentRequestDAO_IFACE {
    void addRequestFromUser(int trainerId, int tournamentId);

    List<Entity_JoinTournamentRequest> getRequestsByTournamentId(int t_id);
}
