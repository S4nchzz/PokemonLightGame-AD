package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_Tournament;
import org.lightPoke.db.entities.Entity_Trainer;

import java.util.List;

public interface TrainerDAO_IFACE {
    void createTrainer(Entity_Trainer entity);
    void removeTrainer(final int trainerId);
    Entity_Trainer getTrainerByUsername(String username);
    Entity_Trainer getTrainerById(int trainerId);
}
