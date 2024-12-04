package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.TrainerOnTourmanetDAO_IMPLE;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.dto.TrainerOnTournamentDTO;
import org.lightPoke.db.entities.Entity_TrainerOnTournament;

import java.util.ArrayList;
import java.util.List;

public class TrainerOnTournamentService {
    private static TrainerOnTournamentService instance;
    private final TrainerOnTourmanetDAO_IMPLE trainerOnTourmanetDAO;

    private TrainerOnTournamentService() {
        this.trainerOnTourmanetDAO = TrainerOnTourmanetDAO_IMPLE.getInstance();
    }

    public static TrainerOnTournamentService getInstance() {
        if (instance == null) {
            instance = new TrainerOnTournamentService();
        }
        return instance;
    }

    private TrainerOnTournamentDTO entityToDto(Entity_TrainerOnTournament entity) {
        return new TrainerOnTournamentDTO(entity.id_tournament(), entity.id_trainer());
    }

    public List<TrainerOnTournamentDTO> getTrainersByTournamentId(int t_id) {
        List<TrainerOnTournamentDTO> dtos = new ArrayList<>();

        for (Entity_TrainerOnTournament e: trainerOnTourmanetDAO.getTrainersByTournamentId(t_id)) {
            dtos.add(entityToDto(e));
        }

        return dtos;
    }

    public void addTrainerToTournament(TrainerDTO trainerDTO, TournamentDTO tournamentDTO) {
        trainerOnTourmanetDAO.addTrainerToTournament(trainerDTO, tournamentDTO);
    }
}