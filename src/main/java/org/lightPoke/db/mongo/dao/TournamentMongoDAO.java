package org.lightPoke.db.mongo.dao;

import com.mongodb.client.MongoDatabase;
import org.lightPoke.db.entity.Ent_Combat;
import org.lightPoke.db.entity.Ent_Tournament;
import org.bson.Document;
import org.lightPoke.db.entity.Ent_Trainer;

import java.util.ArrayList;
import java.util.List;

public class TournamentMongoDAO {
    private MongoDatabase mongoDatabase;

    public TournamentMongoDAO(final MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public void insertTournament(final Ent_Tournament tournament, final List<Ent_Combat> combatList) {
        Document d = new Document("_id", tournament.getId())
                .append("name", tournament.getName())
                .append("region", tournament.getRegion())
                .append("victory_points", tournament.getVictoryPoints())
                .append("t_winner", parseTrainerToDocument(tournament.getTWinner()));

        List<Document> documentCombats = new ArrayList<>();
        for (Ent_Combat c : combatList) {
            Document combatDocument = new Document();
            combatDocument
                .append("_id", c.getId())
                .append("date", c.getDate())
                .append("trainer_1", parseTrainerToDocument(c.getTrainer_1()))
                .append("trainer_2", parseTrainerToDocument(c.getTrainer_2()))
                .append("c_winner", parseTrainerToDocument(c.getC_winner()));

            documentCombats.add(combatDocument);
        }

        d.append("combats", documentCombats);
        mongoDatabase.getCollection("tournament").insertOne(d);
    }

    private Document parseTrainerToDocument (final Ent_Trainer trainer) {
        if (trainer == null) {
            return new Document();
        }

        return new Document()
                .append("_id", trainer.getId())
                .append("username", trainer.getUsername())
                .append("nationality",trainer.getName())
                .append("license", new Document()
                        .append("_id", trainer.getLicense().getId())
                        .append("expedition_Date", trainer.getLicense().getExpedition_Date())
                        .append("points", trainer.getLicense().getPoints())
                        .append("nvictories", trainer.getLicense().getnVictories()));
    }
}
