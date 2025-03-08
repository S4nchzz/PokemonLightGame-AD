package org.lightPoke.db.mongo.collections.models;

import org.springframework.data.mongodb.core.mapping.Field;

public class LicenseModel {
    @Field("id")
    private int id;

    @Field("expeditionDate")
    private String expeditionDate;

    @Field("points")
    private float points;

    @Field("nVictories")
    private int nVictories;

    public LicenseModel() {}

    public LicenseModel(int id, String expeditionDate, float points, int nVictories) {
        this.id = id;
        this.expeditionDate = expeditionDate;
        this.points = points;
        this.nVictories = nVictories;
    }

    // Getters y setters (los añadirás tú)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(String expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public int getNVictories() {
        return nVictories;
    }

    public void setNVictories(int nVictories) {
        this.nVictories = nVictories;
    }
}