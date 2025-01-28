package org.lightPoke.db.services;

import org.lightPoke.db.entity.*;
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

    @Autowired
    private final Svice_License serviceLicense;

    private Svice_Trainer(Repo_Trainer repoTrainer, Svice_License serviceLicense) {
        this.repoTrainer = repoTrainer;
        this.serviceLicense = serviceLicense;
    }

    public void createTrainer(User user) {
        Ent_License license = serviceLicense.createLicense();
        repoTrainer.createTrainer(new Ent_Trainer(((TRUser)user).getUsername(), ((TRUser)user).getNombre(), ((TRUser)user).getNacionalidad(), license));
    }

    public Ent_Trainer getTrainerByUsername(final String username) {
        return repoTrainer.findByUsername(username);
    }

    public Ent_Trainer getTrainerById(final int trainer_id) {
        return repoTrainer.findById(trainer_id).get();
    }
}
