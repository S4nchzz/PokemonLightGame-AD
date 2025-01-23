package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerDAO_IMPLE;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Entity_Combat;
import org.lightPoke.db.repo.Repo_Combat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Svice_Combat {
    private static Svice_Combat instance;

    @Autowired
    private final Repo_Combat repoCombat;

    private Svice_Combat(Repo_Combat repoCombat) {
        this.repoCombat = repoCombat;
    }

    public List<Ent_Combat> getCombatsByTournamentId(final int t_id) {
        List<Entity_Combat> entityCombats = combatDAO.findCombatsByTournamentId(t_id);

        List<Ent_Combat> combats = new ArrayList<>();

        for (Entity_Combat e : entityCombats) {
            combats.add(entityToDto(e));
        }

        return combats;
    }

    public List<Ent_Combat> getCombatsFinishedByTrainerId(int id) {
        List<Entity_Combat> combats = combatDAO.getCombatsFinishedByTrainerId(id);

        List<Ent_Combat> combatDTOS = new ArrayList<>();

        for (Entity_Combat e: combats) {
            combatDTOS.add(entityToDto(e));
        }

        return combatDTOS;
    }

    public void addCombatsToTournament(int tournament_id) {
        for (int i = 0; i < 3; i++) {
            combatDAO.addCombatsToTournament(tournament_id);
        }
    }

    public boolean isTrainerInAnyCombat(int trainer_id) {
        return combatDAO.isTrainerInAnyCombat(trainer_id);
    }

    public void addTrainerToTournamentCombat(int trainer_id, int tournament_id) {
        combatDAO.addTrainerToTournamentCombat(trainer_id, tournament_id);
    }

    public List<Ent_Combat> getCombatsByWinnerId(int trainer_id) {
        List<Entity_Combat> entityCombats = combatDAO.getCombatByWinnerId(trainer_id);

        List<Ent_Combat> combatsDto = new ArrayList<>();

        for (Entity_Combat c : entityCombats) {
            combatsDto.add(entityToDto(c));
        }

        return combatsDto;
    }
}
