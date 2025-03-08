package org.lightPoke.db.mysql.services;

import org.lightPoke.db.mysql.entity.*;
import org.lightPoke.db.mysql.repo.Repo_Trainer;
import org.lightPoke.users.TRUser;
import org.lightPoke.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Svice_Trainer {
    @Autowired
    private Repo_Trainer repoTrainer;

    @Autowired
    private Svice_License serviceLicense;

    @Autowired
    private Svice_Admin_InTournament adminInTournament;

    public void createTrainer(User user) {
        Ent_License license = serviceLicense.createLicense();
        repoTrainer.save(new Ent_Trainer((user).getUsername(), ((TRUser)user).getNombre(), ((TRUser)user).getNacionalidad(), license));
    }

    public Ent_Trainer getTrainerByUsername(final String username) {
        return repoTrainer.findByUsername(username);
    }

    public Ent_Trainer getTrainerById(final int trainer_id) {
        return repoTrainer.findById(trainer_id).get();
    }

    public void changeUsername(String username, String newUsername) {
        Ent_Trainer trainer = repoTrainer.findByUsername(username);

        if (trainer != null) {
            trainer.setUsername(newUsername);
            repoTrainer.save(trainer);
        } else {
            Ent_At_InTournament adminInT = adminInTournament.getTournamentIdByAdminUsername(username);
            if (adminInT != null) {
                adminInT.setAdmin(newUsername);
                adminInTournament.save(adminInT);
            }
        }
    }

    public void removeUserByUsername(String username) {
        repoTrainer.removeByUsername(username);
    }

    public void save(Ent_Trainer trainerWinner) {
        repoTrainer.save(trainerWinner);
    }
}
