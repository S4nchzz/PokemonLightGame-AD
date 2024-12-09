package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_Combat;

import java.util.List;

public interface CombatDAO_IFACE {
    Entity_Combat getCombatById(int id);
    void createCombat(Entity_Combat entity);
    void deleteCombat(Entity_Combat entity);
    List<Entity_Combat> findCombatsByTournamentId(int t_id);
    List<Entity_Combat> getCombatsFinishedByTrainerId(int id);

    void addCombatsToTournament(int tournamentId);

    boolean isTrainerInAnyCombat(int trainerId);

    void addTrainerToTournamentCombat(int trainerId, int tournamentId);

    List<Entity_Combat> getCombatByWinnerId(int trainerId);
}
