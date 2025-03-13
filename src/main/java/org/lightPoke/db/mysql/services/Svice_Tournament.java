package org.lightPoke.db.mysql.services;

import org.lightPoke.db.mysql.entity.Ent_At_InTournament;
import org.lightPoke.db.mysql.entity.Ent_Combat;
import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.lightPoke.db.mysql.repo.Repo_Combat;
import org.lightPoke.db.mysql.repo.Repo_Tournament;
import org.lightPoke.users.ATUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Svice_Tournament {
    @Autowired
    private Repo_Tournament repoTournament;

    @Autowired
    private Repo_Combat repoCombat;

    @Autowired
    private Svice_Combat serviceCombat;

    @Autowired
    private Svice_Admin_InTournament serviceAdminInT;

    public List<Ent_Tournament> getAllTournaments() {
        List<Ent_Tournament> tournaments = repoTournament.findAll();
        return tournaments;
    }

    /**
     * Convertimos el tournament DTO que tiene solo el nombre la region y los puntos de victoria
     * en una entidad y la enviamos a crear el torneo, esta creacion nos devolvera una entidad
     * nueva con el id, la reconvertimos a DTO ya que ahora tiene constancia en la base de datos
     * @param entTournament
     * @param atUser
     */
    public void createTournament(final Ent_Tournament entTournament, final ATUser atUser) {
        repoTournament.save(entTournament);

        serviceCombat.addCombatsToTournament(entTournament);

        serviceAdminInT.addTournamentAdmin(new Ent_At_InTournament(atUser.getUsername(), entTournament));
    }

    public boolean tournamentAlreadyExists(Ent_Tournament enTournament) {
        return !repoTournament.findByNameAndRegion(enTournament.getName(), enTournament.getRegion()).isEmpty();
    }

    public List<Ent_Tournament> getTournamentsByUserId(int id) {
        List<Integer> tournamentsOnInt = repoCombat.findDistinctByTrainerId(id);
        List<Ent_Tournament> tournaments = new ArrayList<>();

        for (Integer i : tournamentsOnInt) {
            tournaments.add(repoTournament.findById(i).get());
        }

        return tournaments;
    }

    public Ent_Tournament getTournamentById(final int t_id) {
        return repoTournament.findById(t_id).get();
    }

    public void setNullTournamentsWinnedByUsername(int w_id) {
        Ent_Tournament tournament = repoTournament.findTournamentByWinner(w_id);

        if (tournament == null) {
            return;
        }

        tournament.setWinner(null);
        repoTournament.save(tournament);
    }

    public void deleteTournamentById(int id) {
        repoTournament.deleteById(id);
    }

    public boolean isTrainerOnEndedTournament(int trainer_id) {
        List<Ent_Combat> combats = repoCombat.findCombatsById(trainer_id);
        boolean combatsAreFinished = true;

        for (Ent_Combat c : combats) {
            if (c.getTrainer_1() == null || c.getTrainer_2() == null || c.getC_winner() == null) {
                combatsAreFinished = false;
            }
        }

        return combatsAreFinished;
    }
}
