package org.lightPoke.db.dao.interfaces;

import org.lightPoke.db.entities.Entity_Trainer;

public interface TrainerDAO_IFACE {
    void createTrainer(Entity_Trainer entity);
    void removeTrainer(final int trainerId);
}
