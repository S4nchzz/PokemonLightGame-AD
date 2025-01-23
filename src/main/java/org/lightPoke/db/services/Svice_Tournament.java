package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Ent_Tournament;
import org.lightPoke.db.entity.Entity_Combat;
import org.lightPoke.db.entity.Entity_Tournament;
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

    private Svice_Tournament(Repo_Tournament repoTournament) {
        this.repoTournament = repoTournament;
    }

    public List<Ent_Tournament> getAllTournaments() {
        List<Ent_Tournament> tournaments = repoTournament.findAll();
        return tournaments;
    }

    /**
     * Convertimos el tournament DTO que tiene solo el nombre la region y los puntos de victoria
     * en una entidad y la enviamos a crear el torneo, esta creacion nos devolvera una entidad
     * nueva con el id, la reconvertimos a DTO ya que ahora tiene constancia en la base de datos
     * @param tournamentDTO
     * @param atUser
     */
    public void createTournament(final Ent_Tournament tournamentDTO, final ATUser atUser) {
        Ent_Tournament tournament = entityToDto(tournamentDAO.createTournament(dtoToEntity(tournamentDTO)));

        Svice_Combat combatService = Svice_Combat.getInstance();
        combatService.addCombatsToTournament(tournament.getId());

        Svice_Admin_InTournament atInTournamentService = Svice_Admin_InTournament.getInstance();
        atInTournamentService.addTournamentAdmin(atUser.getUsername(), tournament.getId());
    }

    public boolean isTournamentAvailable(Ent_Tournament tournamentDTO) {
        return tournamentDAO.getTournamentByNameAndRegion(dtoToEntity(tournamentDTO)) == null;
    }

    public List<Ent_Tournament> getTournamentsByUserId(int id) {
        List<Ent_Tournament> tournaments = new ArrayList<>();

        for (Entity_Tournament e : tournamentDAO.getTournamentsFromUserById(id)) {
            tournaments.add(entityToDto(e));
        }

        List<Ent_Combat> combats =  repoCombat.findByTrainerId(id);

        List<Ent_Tournament> tournaments = repoTournament.findAllById(
                combats.forEach((e) -> {
                    e.getTournament().getId();
                });
        );

        return tournaments;
    }

    public Ent_Tournament getTournamentById(final int t_id) {
        return repoTournament.findById(t_id).get();
    }
}
