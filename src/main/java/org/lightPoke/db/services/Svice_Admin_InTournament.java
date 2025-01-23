package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.At_InTournamentDAO_IMPLE;
import org.lightPoke.db.entity.Ent_At_InTournament;
import org.lightPoke.db.entity.Entity_AT_InTournament;
import org.lightPoke.db.repo.Repo_At_InTournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Svice_Admin_InTournament {
    private static Svice_Admin_InTournament instance;

    @Autowired
    private final Repo_At_InTournament repoAtInTournament;

    private Svice_Admin_InTournament(Repo_At_InTournament repoAtInTournament) {
        this.repoAtInTournament = repoAtInTournament;
    }

    public void addTournamentAdmin(final String adminUsername, final int tournament_id) {
        atInTournamentDAO.createTournamentAdmin(adminUsername, tournament_id);
    }

    public Ent_At_InTournament getTournamentIdByAdminUsername(String username) {
        return entityToDto(atInTournamentDAO.getAt_InTournamentEntityByAdminUsername(username));
    }

    public boolean userExistInDatabase(final String username) {
        return atInTournamentDAO.userExistInDatabase(username);
    }
}
