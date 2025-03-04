package org.lightPoke.db.services;

import org.lightPoke.db.entity.*;
import org.lightPoke.db.repo.Repo_Combat;
import org.lightPoke.db.repo.Repo_License;
import org.lightPoke.db.repo.Repo_Tournament;
import org.lightPoke.db.repo.Repo_Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Svice_Combat {
    @Autowired
    private Repo_Combat repoCombat;

    @Autowired
    private Repo_Tournament repoTournament;

    @Autowired
    private Repo_Trainer repoTrainer;

    @Autowired
    private Repo_License repoLicense;

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

    public void checkTournamentWinner(Ent_Tournament tournament) {
        List<Ent_Combat> combats = repoCombat.findCombatsByTournamentId(tournament.getId());

        int nCombatsEnded = 0;

        for (Ent_Combat c : combats) {
            if (c.getTrainer_1() != null && c.getTrainer_2() != null && c.getC_winner() != null) {
                nCombatsEnded++;
            }
        }

        // Todos los combates han terminado
        if (nCombatsEnded == combats.size()) {
            if (combats.getFirst().getC_winner() == combats.get(1).getC_winner() || combats.getFirst().getC_winner() == combats.getLast().getC_winner()) {
                tournament.setWinner(combats.getFirst().getC_winner());

                Ent_License winnerLicense = combats.getFirst().getC_winner().getLicense();
                winnerLicense.setPoints(tournament.getVictoryPoints());

                repoLicense.save(winnerLicense);
            } else if (combats.get(1).getC_winner() == combats.getFirst().getC_winner() || combats.get(1).getC_winner() == combats.getLast().getC_winner()) {
                tournament.setWinner(combats.get(1).getC_winner());

                Ent_License winnerLicense = combats.get(1).getC_winner().getLicense();
                winnerLicense.setPoints(tournament.getVictoryPoints());

                repoLicense.save(winnerLicense);
            } else if (combats.getLast().getC_winner() == combats.get(1).getC_winner() || combats.getLast().getC_winner() == combats.getFirst().getC_winner()) {
                tournament.setWinner(combats.getLast().getC_winner());

                Ent_License winnerLicense = combats.getLast().getC_winner().getLicense();
                winnerLicense.setPoints(tournament.getVictoryPoints());

                repoLicense.save(winnerLicense);
            } else {
                for (Ent_Combat c : combats) {
                    // No hubo un ganador se resetean los ganadores
                    c.setC_winner(null);
                    repoCombat.save(c);
                }

                return;
            }

            repoTournament.save(tournament);
        }
    }

    public void setNullCombatsByUsername(String username) {
        final Ent_Trainer trainer = repoTrainer.findByUsername(username);
        List<Ent_Combat> combats = repoCombat.findCombatsByUsername(trainer.getId());

        for (Ent_Combat c : combats) {
            if (c.getTrainer_1().getUsername().equals(username)) {
                c.setTrainer_1(null);
            } else if (c.getTrainer_2().getUsername().equals(username)) {
                c.setTrainer_2(null);
            }

            c.setC_winner(null);
            c.setDate(null);

            repoCombat.save(c);
        }

        reOrganizeCombats(combats.getFirst().getTournament().getId());
    }

    private void reOrganizeCombats(int tId) {
        List<Ent_Combat> combats = repoCombat.findCombatsByTournamentId(tId);

        if (combats.getFirst().getTrainer_1() == null && combats.get(1).getTrainer_1() == null) {
            // NULL, ?
            // NULL, ?
            // ?, ?
            combats.getFirst().setTrainer_1(combats.getLast().getTrainer_2());

            combats.getFirst().setC_winner(combats.getLast().getC_winner());
            combats.getLast().setC_winner(null);

            combats.getFirst().setDate(combats.getLast().getDate());
            combats.getLast().setDate(null);

            combats.get(1).setTrainer_1(combats.get(1).getTrainer_2());
            combats.get(1).setC_winner(null);
            combats.get(1).setDate(null);

            combats.get(1).setTrainer_2(null);
            combats.get(2).setTrainer_2(null);

            updateCombats(combats);
        } else if (combats.get(2).getTrainer_1() == null && combats.getFirst().getTrainer_2() == null) {
            //  ?,  NULL
            //  ?,   ?
            // NULL, ?
            combats.getLast().setTrainer_1(combats.getLast().getTrainer_2());
            combats.getLast().setC_winner(null);
            combats.getLast().setDate(null);

            combats.getFirst().setTrainer_2(combats.get(1).getTrainer_2());

            combats.getFirst().setDate(combats.get(1).getDate());
            combats.get(1).setDate(null);

            combats.getFirst().setC_winner(combats.get(1).getC_winner());
            combats.get(1).setC_winner(null);

            combats.get(1).setTrainer_2(null);
            combats.get(2).setTrainer_2(null);

            updateCombats(combats);
        }
    }

    private void updateCombats(List<Ent_Combat> combats) {
        for (Ent_Combat c : combats) {
            repoCombat.saveAndFlush(c);
        }
    }

    public void deleteCombatsByTournamentId(int id) {
        repoCombat.deleteCombatsByTournamentId(id);
    }
}
