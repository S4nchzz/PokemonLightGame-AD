package org.lightPoke.db.services;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerDAO_IMPLE;
import org.lightPoke.db.dto.CombatDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_Combat;
import org.lightPoke.log.LogManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CombatService {
    private static CombatService instance;
    private final CombatDAO_IMPLE combatDAO;

    private CombatService() {
        this.combatDAO = CombatDAO_IMPLE.getInstance();
    }

    public static CombatService getInstance() {
        if (instance == null) {
            instance = new CombatService();
        }

        return instance;
    }

    private CombatDTO entityToDto(final Entity_Combat entity) {
        TrainerService trainerService = TrainerService.getInstance();

        TrainerDTO trainer1 = null;
        TrainerDTO trainer2 = null;
        TrainerDTO c_winner = null;

        if (entity.trainer_1() != -1) {
            trainer1 = trainerService.getTrainerById(entity.trainer_1());
        }

        if (entity.trainer_2() != -1) {
            trainer2 = trainerService.getTrainerById(entity.trainer_2());
        }

        if (entity.c_winner() != -1) {
            c_winner = trainerService.getTrainerById(entity.c_winner());
        }

        return new CombatDTO(entity.date(), trainer1, trainer2, c_winner);
    }

    public List<CombatDTO> getCombatsByTournamentId(final int t_id) {
        List< Entity_Combat> entityCombats = combatDAO.findCombatsByTournamentId(t_id);

        List<CombatDTO> combats = new ArrayList<>();

        for (Entity_Combat e : entityCombats) {
            combats.add(entityToDto(e));
        }

        return combats;
    }

    public List<CombatDTO> getCombatsByTrainerId(int id) {
        List<Entity_Combat> combats = combatDAO.findCombatsByTrainerId(id);

        List<CombatDTO> combatDTOS = new ArrayList<>();

        for (Entity_Combat e: combats) {
            combatDTOS.add(entityToDto(e));
        }

        return combatDTOS;
    }

    public void addCombatsToTournament(int tournament_id) {
        for (int i = 0; i < 3; i++) {
            combatDAO.addCombatsToTournament(tournament_id);
        }
    }
}
