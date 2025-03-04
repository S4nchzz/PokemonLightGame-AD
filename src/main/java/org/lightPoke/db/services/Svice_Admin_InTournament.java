package org.lightPoke.db.services;

import org.lightPoke.db.entity.Ent_At_InTournament;
import org.lightPoke.db.repo.Repo_At_InTournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Svice_Admin_InTournament {
    @Autowired
    private Repo_At_InTournament repoAtInTournament;

    public void addTournamentAdmin(Ent_At_InTournament entAtInTournament) {
        repoAtInTournament.save(entAtInTournament);
    }

    public Ent_At_InTournament getTournamentIdByAdminUsername(String username) {
        return repoAtInTournament.findByAdmin(username);
    }

    public boolean userExistInDatabase(final String username) {
        return repoAtInTournament.findByAdmin(username) != null;
    }

    public void save(Ent_At_InTournament adminInT) {
        repoAtInTournament.save(adminInT);
    }

    public void deleteByAdminUsername(int id) {
        repoAtInTournament.deleteById(id);
    }

    public List<Integer> getRequestsForTournament(int id) {
        return repoAtInTournament.getRequestsForTournament(id);
    }
}
