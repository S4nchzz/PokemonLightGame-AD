package org.lightPoke.db.services;

import com.google.protobuf.TimestampOrBuilder;
import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.LicenseDAO_IMPLE;
import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerDAO_IMPLE;
import org.lightPoke.db.dto.*;
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

    private TrainerService() {
        this.trainerDao = TrainerDAO_IMPLE.getInstance();
    }

    public static TrainerService getInstance() {
        if (instance == null) {
            instance = new TrainerService();
        }

        return instance;
    }

    private TrainerDTO entityToDto(final Entity_Trainer entityTrainer) {
        LicenseService licenseService = LicenseService.getInstance();

        LicenseDTO license = licenseService.getLicenseByTrainerId(entityTrainer.id());
        return new TrainerDTO(entityTrainer.id(), entityTrainer.username(), entityTrainer.name(), entityTrainer.nationality(), license);
    }

    public void createTrainer(User user) {
        LicenseService licenseService = LicenseService.getInstance();

        LicenseDTO license = licenseService.createLicense();
        trainerDao.createTrainer(new Entity_Trainer(((TRUser)user).getUsername(), ((TRUser)user).getNombre(), ((TRUser)user).getNacionalidad(), license.getId()));
    }

    public TrainerDTO getTrainerByUsername(final String username) {
        Entity_Trainer trainerEntity = trainerDao.getTrainerByUsername(username);
        return entityToDto(trainerEntity);
    }

    public TrainerDTO getTrainerById(final int trainer_id) {
        return entityToDto(trainerDao.getTrainerById(trainer_id));
    }
}
