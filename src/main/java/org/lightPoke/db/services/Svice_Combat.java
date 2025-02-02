package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_At_InTournament;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.entity.Ent_Trainer;
import org.lightPoke.db.repo.Repo_Combat;
import org.lightPoke.db.repo.Repo_Tournament;
import org.lightPoke.db.repo.Repo_Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Svice_Combat {
    @Autowired
    private Repo_Combat repoCombat;

    @Autowired
    private Repo_Tournament repoTournament;

    @Autowired
    private Repo_Trainer repoTrainer;

    public List<Ent_Combat> getCombatsByTournamentId(final int t_id) {
        return repoCombat.findByTournamentId(t_id);
    }

    public List<Ent_Combat> getCombatsFinishedByTrainerId(int id) {
        return repoCombat.findCombatsFinishedByTrainerId(id);
    }

    public void addCombatsToTournament(Ent_Tournament tournament) {
        for (int i = 0; i < 3; i++) {
            repoCombat.saveAndFlush(new Ent_Combat(tournament));
        }
    }

    public boolean isTrainerInAnyCombat(int trainer_id) {
        List<Ent_Combat> combats = repoCombat.findByTrainerId(trainer_id);
        return combats != null && !combats.isEmpty();
    }

    public void addTrainerToTournamentCombat(int trainer_id, int tournament_id) {
        List<Ent_Combat> combats = repoCombat.findCombatsByTournamentId(tournament_id);

        if (combats.size() > 3) {
            return;
        }

        int countTrainers = 0;
        for (Ent_Combat c : combats) {
            if (c.getTrainer_1() != null) {
                countTrainers++;
            }

            if (c.getTrainer_2() != null) {
                countTrainers++;
            }
        }

        switch (countTrainers) {
            case 0 -> {
                Ent_Combat firstCombat = combats.getFirst();
                Ent_Combat seccondCombat = combats.get(1);

                Ent_Trainer trainerToUpdate = repoTrainer.findById(trainer_id).get();
                firstCombat.setTrainer_1(trainerToUpdate);
                seccondCombat.setTrainer_1(trainerToUpdate);

                repoCombat.saveAndFlush(firstCombat);
                repoCombat.saveAndFlush(seccondCombat);
            }

            case 2 -> {
                Ent_Combat firstCombat = combats.getFirst();
                Ent_Combat thirdCombat = combats.get(2);

                Ent_Trainer trainerToUpdate = repoTrainer.findById(trainer_id).get();
                firstCombat.setTrainer_2(trainerToUpdate);
                thirdCombat.setTrainer_1(trainerToUpdate);

                repoCombat.saveAndFlush(firstCombat);
                repoCombat.saveAndFlush(thirdCombat);
            }

            case 4 -> {
                Ent_Combat seccondCombat = combats.get(1);
                Ent_Combat thirdCombat = combats.get(2);

                Ent_Trainer trainerToUpdate = repoTrainer.findById(trainer_id).get();
                seccondCombat.setTrainer_2(trainerToUpdate);
                thirdCombat.setTrainer_2(trainerToUpdate);

                repoCombat.saveAndFlush(seccondCombat);
                repoCombat.saveAndFlush(thirdCombat);
            }
        }
    }

    public List<Ent_Combat> getCombatsByWinnerId(int trainer_id) {
        return repoCombat.findByWinnerId(trainer_id);
    }

    public void updateCombat(Ent_Combat combatChoiced) {
        repoCombat.saveAndFlush(combatChoiced);
    }
}
