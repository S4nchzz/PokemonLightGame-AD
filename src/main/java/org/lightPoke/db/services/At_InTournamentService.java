package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.At_InTournamentDAO_IMPLE;
import org.lightPoke.db.dto.At_InTournamentDTO;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.entities.Entity_AT_InTournament;

public class At_InTournamentService {
    private static At_InTournamentService instance;
    private At_InTournamentDAO_IMPLE atInTournamentDAO;

    private At_InTournamentService() {
        this.atInTournamentDAO = At_InTournamentDAO_IMPLE.getInstance();
    }

    public static At_InTournamentService getInstance() {
        if (instance == null) {
            instance = new At_InTournamentService();
        }

        return instance;
    }

    private At_InTournamentDTO entityToDto(final Entity_AT_InTournament entity) {
        TournamentService tournamentService = TournamentService.getInstance();
        TournamentDTO tournamentDTO = tournamentService.getTournamentById(entity.tournament_id());
        return new At_InTournamentDTO(entity.id(), entity.username(), tournamentDTO);
    }

    public void addTournamentAdmin(final String adminUsername, final int tournament_id) {
        atInTournamentDAO.createTournamentAdmin(adminUsername, tournament_id);
    }

    public At_InTournamentDTO getTournamentIdByAdminUsername(String username) {
        return entityToDto(atInTournamentDAO.getAt_InTournamentEntityByAdminUsername(username));
    }

    public boolean userExistInDatabaseAsAT(final String username) {
        return atInTournamentDAO.userExistInDatabaseAsAT(username);
    }
}
