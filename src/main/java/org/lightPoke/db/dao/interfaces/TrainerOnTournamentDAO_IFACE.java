package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_TrainerOnTournament;

import java.util.List;

public interface TrainerOnTournamentDAO_IFACE {
    void addTrainerToTournament(TrainerDTO trainerDTO, TournamentDTO tournamentDTO);
    List<Entity_TrainerOnTournament> getTrainersByTournamentId(int t_id);
}
