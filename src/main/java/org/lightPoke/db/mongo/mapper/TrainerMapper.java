package org.lightPoke.db.mongo.mapper;

import org.lightPoke.db.mongo.collections.TrainerCollection;
import org.lightPoke.db.mongo.collections.models.LicenseModel;
import org.lightPoke.db.mongo.collections.models.TrainerModel;
import org.lightPoke.db.mysql.entity.Ent_Trainer;
import org.lightPoke.db.mysql.entity.Ent_License;

public class TrainerMapper {
    public static TrainerCollection mapEntityToCollection(Ent_Trainer trainer) {
        if (trainer == null) {
            return null;
        }
        return new TrainerCollection(
                trainer.getId(),
                trainer.getUsername(),
                trainer.getNationality(),
                mapEntityLicenseToModel(trainer.getLicense())
        );
    }

    private static LicenseModel mapEntityLicenseToModel(Ent_License entity) {
        if (entity == null) {
            return null;
        }
        return new LicenseModel(
                entity.getId(),
                entity.getExpedition_Date(),
                entity.getPoints(),
                entity.getnVictories()
        );
    }

    public static TrainerCollection mapModelToCollection(TrainerModel model) {
        if (model == null) {
            return null;
        }
        return new TrainerCollection(
                model.getId(),
                model.getUsername(),
                model.getNationality(),
                model.getLicense()
        );
    }
}