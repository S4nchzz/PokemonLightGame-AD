package org.lightPoke.db.services;

import org.lightPoke.db.dao.services.CombatDAO_IMPLE;
import org.lightPoke.db.dao.services.LicenseDAO_IMPLE;
import org.lightPoke.db.dao.services.TournamentDAO_IMPLE;
import org.lightPoke.db.dao.services.TrainerDAO_IMPLE;
import org.lightPoke.db.entity.*;
import org.lightPoke.db.entity.Entity_Combat;
import org.lightPoke.db.entity.Entity_License;
import org.lightPoke.db.entity.Entity_Tournament;
import org.lightPoke.db.entity.Entity_Trainer;
import org.lightPoke.db.repo.Repo_Trainer;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Svice_Trainer {
    private static Svice_Trainer instance;

    @Autowired
    private final Repo_Trainer repoTrainer;

    private Svice_Trainer(Repo_Trainer repoTrainer) {
        this.repoTrainer = repoTrainer;
    }

    public void createTrainer(User user) {
        Svice_License licenseService = Svice_License.getInstance();

        Ent_License license = licenseService.createLicense();
        trainerDao.createTrainer(new Entity_Trainer(((TRUser)user).getUsername(), ((TRUser)user).getNombre(), ((TRUser)user).getNacionalidad(), license.getId()));
    }

    public Ent_Trainer getTrainerByUsername(final String username) {
        Ent_Trainer trainer = repoTrainer.findByUsername(username);
        return trainer;
    }

    public Ent_Trainer getTrainerById(final int trainer_id) {
        return repoTrainer.findById(trainer_id).get();
    }
}
