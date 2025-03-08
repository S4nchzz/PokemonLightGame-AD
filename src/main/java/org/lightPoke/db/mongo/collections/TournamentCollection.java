package org.lightPoke.db.mongo.collections;

import org.lightPoke.db.mongo.collections.models.CombatModel;
import org.lightPoke.db.mongo.collections.models.TrainerModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collation = "tournament")
public class TournamentCollection {
    @Id
    private int id;

    @Field("name")
    private String name;

    @Field("region")
    private String region;

    @Field("victory_points")
    private float victory_points;

    @Field("t_winner")
    private TrainerModel t_winner;

    @Field("combats")
    private List<CombatModel> combats;

    public TournamentCollection() {}

    public TournamentCollection(int id, String name, String region, float victory_points, TrainerModel t_winner, List<CombatModel> combats) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.victory_points = victory_points;
        this.t_winner = t_winner;
        this.combats = combats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public float getVictory_points() {
        return victory_points;
    }

    public void setVictory_points(float victory_points) {
        this.victory_points = victory_points;
    }

    public TrainerModel getT_winner() {
        return t_winner;
    }

    public void setT_winner(TrainerModel t_winner) {
        this.t_winner = t_winner;
    }

    public List<CombatModel> getCombats() {
        return combats;
    }

    public void setCombats(List<CombatModel> combats) {
        this.combats = combats;
    }
}
