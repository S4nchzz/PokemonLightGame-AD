package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerOnTourmanetDAO_IMPLE;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_Tournament;
import org.lightPoke.db.entities.Entity_TrainerOnTournament;

import java.util.ArrayList;
import java.util.List;

public class TournamentService {
    private static TournamentService instance;
    private final TournamentDAO_IMPLE tournamentDAO;

    private TournamentService() {
        this.tournamentDAO = TournamentDAO_IMPLE.getInstance();
    }

    public static TournamentService getInstance() {
        if (instance == null) {
            instance = new TournamentService();
        }

        return instance;
    }

    public List<TournamentDTO> getAllTournaments() {
        List<Entity_Tournament> entity_tournaments = tournamentDAO.getAllTournaments();
        
        return entityTournamentListToDto(entity_tournaments);
    }

    private List<TournamentDTO> entityTournamentListToDto(List<Entity_Tournament> entityTournaments) {
        List<TournamentDTO> tournamentDTO = new ArrayList<>();

        for (Entity_Tournament t : entityTournaments) {
            tournamentDTO.add(new TournamentDTO(t.name(), t.cod_region(), t.victory_points()));
        }

        return tournamentDTO;
    }

    public void addTrainerToTournament(TrainerDTO trainerDTO, TournamentDTO tournamentDTO) {
        TrainerOnTourmanetDAO_IMPLE trainerOnTourmanetDAO = TrainerOnTourmanetDAO_IMPLE.getInstance();
        trainerOnTourmanetDAO.addTrainerToTournament(trainerDTO, tournamentDTO);
    }
}
