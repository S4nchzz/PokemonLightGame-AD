package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_Combat;

import java.util.List;

public interface CombatDAO_IFACE {
    Entity_Combat getCombatById(int id);
    void createCombat(Entity_Combat entity);
    void deleteCombat(Entity_Combat entity);
    List<Entity_Combat> findCombatsByTournamentId(int t_id);
}
