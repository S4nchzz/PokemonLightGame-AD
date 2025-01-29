package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_At_InTournament;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.repo.Repo_Combat;
import org.lightPoke.db.repo.Repo_Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Svice_Combat {
    @Autowired
    private Repo_Combat repoCombat;

    @Autowired
    private Repo_Tournament repoTournament;

    public List<Ent_Combat> getCombatsByTournamentId(final int t_id) {
        return repoCombat.findByTournamentId(t_id);
    }

    public List<Ent_Combat> getCombatsFinishedByTrainerId(int id) {
        return repoCombat.findCombatsFinishedByTrainerId(id);
    }

    public void addCombatsToTournament(Ent_Tournament tournament) {
        for (int i = 0; i < 3; i++) {
            repoCombat.save(new Ent_Combat(tournament));
        }
    }

    public boolean isTrainerInAnyCombat(int trainer_id) {
        List<Ent_Combat> combats = repoCombat.findByTrainerId(trainer_id);
        return combats != null || !combats.isEmpty();
    }

    public void addTrainerToTournamentCombat(int trainer_id, int tournament_id) {
        repoCombat.addTrainerToTournamentCombat(trainer_id, tournament_id);
    }

    public List<Ent_Combat> getCombatsByWinnerId(int trainer_id) {
        return repoCombat.findByWinnerId(trainer_id);
    }
}
