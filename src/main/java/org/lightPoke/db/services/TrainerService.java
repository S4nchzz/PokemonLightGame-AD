package org.lightPoke.db.services;

import com.google.protobuf.TimestampOrBuilder;
import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.LicenseDAO_IMPLE;
import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerDAO_IMPLE;
import org.lightPoke.db.dto.CombatDTO;
import org.lightPoke.db.dto.LicenseDTO;
import org.lightPoke.db.dto.TournamentDTO;
import org.lightPoke.db.dto.TrainerDTO;
import org.lightPoke.db.entities.Entity_Combat;
import org.lightPoke.db.entities.Entity_License;
import org.lightPoke.db.entities.Entity_Tournament;
import org.lightPoke.db.entities.Entity_Trainer;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainerService {
    private static TrainerService instance;
    private final TrainerDAO_IMPLE trainerDao;
    private final TournamentDAO_IMPLE tournamentDao;
    private final CombatDAO_IMPLE combatDao;
    private final LicenseDAO_IMPLE licenseDAO;

    private TrainerService() {
        this.trainerDao = TrainerDAO_IMPLE.getInstance();
        this.tournamentDao = TournamentDAO_IMPLE.getInstance();
        this.combatDao = CombatDAO_IMPLE.getInstance();
        this.licenseDAO = LicenseDAO_IMPLE.getInstance();
    }

    public static TrainerService getInstance() {
        if (instance == null) {
            instance = new TrainerService();
        }

        return instance;
    }

    public void createTrainer(User user) {
        Entity_License license = licenseDAO.createLicense();
        trainerDao.createTrainer(new Entity_Trainer(((TRUser)user).getUsername(), ((TRUser)user).getNombre(), ((TRUser)user).getNacionalidad(), license.id()));
    }

    public TrainerDTO getTrainer(final String username) {
        Entity_Trainer trainerEntity = trainerDao.getTrainer(username);
        List<Entity_Tournament> tournamentsFromUser = tournamentDao.getTournamentsFromUserById(trainerEntity.id());
        List<TournamentDTO> tournamentsDTO = tournamentListEntityToDto(tournamentsFromUser);

        List<Entity_Combat> combatsFromUser = new ArrayList<>();
        List<CombatDTO> combatsDTO = combatListEntityToDto(combatsFromUser);

        for (Entity_Tournament tournament : tournamentsFromUser) {
            combatsFromUser.addAll(combatDao.findCombatsByTournamentId(tournament.id()));
        }

        return new TrainerDTO(trainerEntity.id(), trainerEntity.username(), trainerEntity.name(), trainerEntity.nationality(), licenseDAO.entityToDto(licenseDAO.getLicenseByUsername(username)),tournamentsDTO, combatsDTO);
    }

    private List<CombatDTO> combatListEntityToDto(List<Entity_Combat> entityCombats) {
        List<CombatDTO> combats = new ArrayList<>();

        for (Entity_Combat entity : entityCombats) {
            combats.add(new CombatDTO(entity.date(), entity.trainer_1(), entity.trainer_2(), entity.c_winner()));
        }

        return combats;
    }

    private List<TournamentDTO> tournamentListEntityToDto(List<Entity_Tournament> entityTournaments) {
        List<TournamentDTO> tournaments = new ArrayList<>();

        for (Entity_Tournament entity : entityTournaments) {
            tournaments.add(new TournamentDTO(entity.id(), entity.name(), entity.cod_region(), entity.victory_points()));
        }

        return tournaments;
    }
}
