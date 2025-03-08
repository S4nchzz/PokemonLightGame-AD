package org.lightPoke.db.mongo.mapper;

import org.bson.Document;
import org.lightPoke.db.mongo.collections.TournamentCollection;
import org.lightPoke.db.mongo.collections.models.CombatModel;
import org.lightPoke.db.mongo.collections.models.LicenseModel;
import org.lightPoke.db.mongo.collections.models.TrainerModel;
import org.lightPoke.db.mysql.entity.Ent_Combat;
import org.lightPoke.db.mysql.entity.Ent_License;
import org.lightPoke.db.mysql.entity.Ent_Tournament;
import org.lightPoke.db.mysql.entity.Ent_Trainer;

import java.util.ArrayList;
import java.util.List;

public class TournamentMapper {
    public static TournamentCollection mapTournamentEntityToCollection(Ent_Tournament tournament, List<Ent_Combat> combatList) {
        List<CombatModel> combatsModel = new ArrayList<>();
        for (Ent_Combat c : combatList) {
            combatsModel.add(mapEntityCombatToModel(c));
        }

        return new TournamentCollection(
                tournament.getId(),
                tournament.getName(),
                tournament.getRegion(),
                tournament.getVictoryPoints(),
                mapEntityTrainerToModel(tournament.getTWinner()),
                combatsModel
        );
    }

    public static TournamentCollection mapTournamentEntityToCollection(Ent_Tournament tournament) {
        return new TournamentCollection(
                tournament.getId(),
                tournament.getName(),
                tournament.getRegion(),
                tournament.getVictoryPoints(),
                mapEntityTrainerToModel(tournament.getTWinner()),
                null
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

    private static TrainerModel mapEntityTrainerToModel(Ent_Trainer entity) {
        if (entity == null) {
            return null;
        }

        return new TrainerModel(
                entity.getId(),
                entity.getUsername(),
                entity.getNationality(),
                mapEntityLicenseToModel(entity.getLicense())
        );
    }

    private static CombatModel mapEntityCombatToModel(Ent_Combat entity) {
        if (entity == null) {
            return null;
        }

        return new CombatModel(
                entity.getId(),
                entity.getDate(),
                mapEntityTrainerToModel(entity.getTrainer_1()),
                mapEntityTrainerToModel(entity.getTrainer_2()),
                mapEntityTrainerToModel(entity.getC_winner())
        );
    }
}
