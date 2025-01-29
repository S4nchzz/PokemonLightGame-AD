package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_At_InTournament;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.repo.Repo_Combat;
import org.lightPoke.db.repo.Repo_Tournament;
import org.lightPoke.users.ATUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Svice_Tournament {
    private static Svice_Tournament instance;

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

    public boolean isTournamentAvailable(Ent_Tournament enTournament) {
        return repoTournament.findById(enTournament.getId()).isPresent();
    }

    public List<Ent_Tournament> getTournamentsByUserId(int id) {
        List<Ent_Combat> combats =  repoCombat.findByTrainerId(id);

        List<Ent_Tournament> tournaments = new ArrayList<>();
        for (Ent_Combat combat : combats) {
            tournaments.add(repoTournament.findById(combat.getTournament().getId()).get());
        }

        return tournaments;
    }

    public Ent_Tournament getTournamentById(final int t_id) {
        return repoTournament.findById(t_id).get();
    }
}
